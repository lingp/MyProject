package com.lin.service;

import com.lin.netty.ChatMsg;
import com.lin.pojo.Users;
import com.lin.pojo.vo.FriendsRequestVO;
import com.lin.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UserService {

    public boolean queryUsernameIsExist(String name);


    public Users queryUserForLogin(String username, String pwd);


    public Users saveUser(Users user);

    public Users updateUserInfo(Users user);

    public Users queryUserById(String id);

    public Integer preconditionSearchFriends(String myUserId, String friendUsername);

    public Users queryUserInfoByUsername(String username);

    public void sendFriendRequest(String myUserId, String friendUsername);

    public List<FriendsRequestVO> queryFriendRequestList(String acceptUserId);

    public void deleteFriendRequest(String sendUserId, String acceptUserId);

    public void passFriendRequest(String sendUserId, String acceptUserId);

    public List<MyFriendsVO> getMyFriends(String userId);

    public String saveMsg(ChatMsg chatMsg); // 注意是这个com.lin.netty.ChatMsg

    public void updateMsgSigned(List<String> msgIdList);

    public List<com.lin.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
}
