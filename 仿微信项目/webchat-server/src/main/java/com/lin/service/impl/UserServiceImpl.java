package com.lin.service.impl;

import com.lin.enums.MsgActionEnum;
import com.lin.enums.MsgSignFlagEnum;
import com.lin.enums.SearchFriendsStatusEnum;
import com.lin.mapper.*;
import com.lin.netty.ChatMsg;
import com.lin.netty.DataContent;
import com.lin.netty.UserChannelRef;
import com.lin.pojo.FriendsRequest;
import com.lin.pojo.MyFriends;
import com.lin.pojo.Users;
import com.lin.pojo.vo.FriendsRequestVO;
import com.lin.pojo.vo.MyFriendsVO;
import com.lin.service.UserService;
import com.lin.utils.FastDFSClient;
import com.lin.utils.FileUtils;
import com.lin.utils.JsonUtils;
import com.lin.utils.QRCodeUtils;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import io.netty.channel.Channel;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private UsersMapperCustom usersMapperCustom;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    public boolean queryUsernameIsExist(String name) {

        Users user = new  Users();
        user.setUsername(name);
        Users result = usersMapper.selectOne(user);
        return result != null ? true : false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", pwd);

        Users users =  usersMapper.selectOneByExample(userExample);

        return users;
    }

    @Override
    public Users saveUser(Users user) {

        String userId = sid.nextShort();
        // TODO 为每个用户生成一个唯一的二维码
        String qrCodePath = "E:\\CodeDemo\\JavaDemo\\" + userId + "qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath, "mychat:" + user.getUsername());
        MultipartFile qrCodeFile =  FileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
            user.setQrcode(qrCodeUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.setId(userId);

        usersMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(Users user) {
       usersMapper.updateByPrimaryKeySelective(user);
       return queryUserById(user.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {

        Users user = queryUserInfoByUsername(friendUsername);
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }

        if (user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }

        Example friend = new Example(MyFriends.class);
        Example.Criteria fc = friend.createCriteria();
        fc.andEqualTo("myUserId", myUserId);
        fc.andEqualTo("myFriendUserId", user.getId());
        MyFriends myFriend = myFriendsMapper.selectOneByExample(friend);

        if (myFriend != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByUsername(String username) {
        Example user = new Example(Users.class);
        Example.Criteria uc = user.createCriteria();
        uc.andEqualTo("username", username);
        return usersMapper.selectOneByExample(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {


        Users friend = queryUserInfoByUsername(friendUsername);

        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendsRequest friendsRequest = friendsRequestMapper.selectOneByExample(fre);

        if (friendsRequest == null) {
            String requestId = sid.nextShort();
            FriendsRequest request = new FriendsRequest();
            request.setId(requestId);
            request.setSendUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequestDateTime(new Date());
            friendsRequestMapper.insert(request);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendsRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersMapperCustom.queryFriendRequestList(acceptUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", sendUserId);
        frc.andEqualTo("acceptUserId", acceptUserId);
        friendsRequestMapper.deleteByExample(fre);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);

        // 使用websocket主动推送消息，更新通讯录
        Channel sendChannel =  UserChannelRef.get(sendUserId);
        if (sendChannel != null) {
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContent
                            )
                    )
            );
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFriends(String sendUserId, String acceptUserId) {
        Example oFriend = new Example(MyFriends.class);
        Example.Criteria aFriendC = oFriend.createCriteria();
        aFriendC.andEqualTo("myUserId", acceptUserId);
        aFriendC.andEqualTo("myFriendUserId", sendUserId);

        List<MyFriends> mList = myFriendsMapper.selectByExample(oFriend);

        // 避免重复提交
        if (mList.isEmpty()) {
            String recordId = sid.nextShort();
            MyFriends myFriends = new MyFriends();
            myFriends.setMyUserId(acceptUserId);
            myFriends.setMyFriendUserId(sendUserId);
            myFriends.setId(recordId);
            myFriendsMapper.insert(myFriends);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVO> getMyFriends(String userId) {
        List<MyFriendsVO> myFriends = usersMapperCustom.getMyFriends(userId);
        return myFriends;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.lin.pojo.ChatMsg msgDb = new com.lin.pojo.ChatMsg();
        String msgId = sid.nextShort();
        msgDb.setId(msgId);
        msgDb.setAcceptUserId(chatMsg.getReceiveId());
        msgDb.setSendUserId(chatMsg.getSendId());
        msgDb.setSignFlag(MsgSignFlagEnum.unsign.type == 1);
        msgDb.setMsg(chatMsg.getMsg());
        chatMsgMapper.insert(msgDb);
        return msgId;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        usersMapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<com.lin.pojo.ChatMsg> getUnReadMsgList(String acceptUserId) {
        Example chatE = new Example(com.lin.pojo.ChatMsg.class);
        Example.Criteria chatC = chatE.createCriteria();
        chatC.andEqualTo("signFlag", 0);
        chatC.andEqualTo("acceptUserId", acceptUserId);

        List<com.lin.pojo.ChatMsg> rs = chatMsgMapper.selectByExample(chatE);

        return rs;
    }

}
