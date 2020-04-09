package com.lin.mapper;

import com.lin.pojo.Users;
import com.lin.pojo.vo.FriendsRequestVO;
import com.lin.pojo.vo.MyFriendsVO;
import com.lin.utils.MyMapper;

import java.util.List;

public interface UsersMapperCustom extends MyMapper<Users> {

    public List<FriendsRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> getMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);

}