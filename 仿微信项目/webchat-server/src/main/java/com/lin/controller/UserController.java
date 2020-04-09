package com.lin.controller;

import com.lin.enums.OperatorFriendRequestTypeEnum;
import com.lin.enums.SearchFriendsStatusEnum;
import com.lin.pojo.ChatMsg;
import com.lin.pojo.Users;
import com.lin.pojo.bo.UsersBO;
import com.lin.pojo.vo.MyFriendsVO;
import com.lin.pojo.vo.UsersVO;
import com.lin.service.UserService;
import com.lin.utils.FastDFSClient;
import com.lin.utils.FileUtils;
import com.lin.utils.JSONResult;
import com.lin.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("u")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("/regist-or-login")
    public JSONResult registOrLogin(@RequestBody Users user) throws Exception {
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JSONResult.errorMsg("用户名或密码不能为空....");
        }

        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());

        Users userResult = null;
        // 有则登录，无则注册
        if (usernameIsExist) {
            userResult = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
            if (userResult == null) {
                return JSONResult.errorMsg("用户名/密码错误");
            }
        } else {
            user.setNickname(user.getUsername());
            user.setFaceImg("");
            user.setFaceImgBig("");
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            userResult = userService.saveUser(user);
        }

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);

        return JSONResult.ok(usersVO);
    }

    @PostMapping("/uploadFaceBase64")
    public JSONResult uploadFaceBase64(@RequestBody UsersBO usersBO) throws Exception {
        String base64Data = usersBO.getFaceImgData();
        String imgPath = "E:\\CodeDemo\\JavaDemo\\" + usersBO.getUserId() + "userface64.png";
        FileUtils.base64ToFile(imgPath, base64Data);
        MultipartFile imgFile = FileUtils.fileToMultipart(imgPath);

        String url = fastDFSClient.uploadBase64(imgFile);
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String thumpImgUrl = arr[0] + thump + arr[1];

        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setFaceImgBig(url);
        user.setFaceImg(thumpImgUrl);

        Users result = userService.updateUserInfo(user);

        return JSONResult.ok(result);
    }

    @PostMapping("/setNickName")
    public JSONResult setNickName(@RequestBody UsersBO usersBO) throws Exception {
        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setNickname(usersBO.getNickname());
        Users result = userService.updateUserInfo(user);
        return JSONResult.ok(result);
    }

    @PostMapping("/searchFriends")
    public JSONResult searchFriends(String myUserId, String friendUsername) throws Exception {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("");
        }
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            Users user = userService.queryUserInfoByUsername(friendUsername);
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(user, usersVO);
            return JSONResult.ok(usersVO);
        } else {
           String errMsg = SearchFriendsStatusEnum.getMsgByKey(status);
           return JSONResult.errorMsg(errMsg);
        }
    }

    @PostMapping("/addFriendRequest")
    public JSONResult addFriendRequest(String myUserId, String friendUsername) throws Exception {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("");
        }
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            userService.sendFriendRequest(myUserId, friendUsername);
            return JSONResult.ok();
        } else {
            String errMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errMsg);
        }
    }

    @PostMapping("/queryFriendRequestList")
    public JSONResult queryFriendRequestList(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        return JSONResult.ok(userService.queryFriendRequestList(userId));
    }

    @PostMapping("/operFriendRequest")
    public JSONResult operFriendRequest(String acceptUserId, String sendUserId, Integer oType) throws Exception {
        if (StringUtils.isBlank(acceptUserId)
                || StringUtils.isBlank(sendUserId)
                || oType == null) {
            return JSONResult.errorMsg("");
        }

        if(StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(oType))) {
            return JSONResult.errorMsg("");
        }

        if (oType == OperatorFriendRequestTypeEnum.IGNORE.type) {
            userService.deleteFriendRequest(sendUserId, acceptUserId);
        } else if (oType == OperatorFriendRequestTypeEnum.PASS.type) {
            userService.passFriendRequest(sendUserId, acceptUserId);
        }

        List<MyFriendsVO> myFriendsVOList = userService.getMyFriends(acceptUserId);


        return JSONResult.ok(myFriendsVOList);
    }


    @PostMapping("/myFriends")
    public JSONResult myFriends(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        List<MyFriendsVO> myFriendsVOList = userService.getMyFriends(userId);
        return JSONResult.ok(myFriendsVOList);
    }

    @PostMapping("/getUnReadMsgList")
    public JSONResult getUnReadMsgList(String acceptUserId) throws Exception {
        if (StringUtils.isBlank(acceptUserId)) {
            return JSONResult.errorMsg("");
        }
        List<ChatMsg> list = userService.getUnReadMsgList(acceptUserId);
        return JSONResult.ok(list);
    }
}
