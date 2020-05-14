package com.wen.friend.service;


import com.wen.common.result.ResultService;

public interface FriendService {


    ResultService<Void> addFriend(String friendId, String userId);

    ResultService<Void> addNoFriend(String friendId, String userId);

    void deleteFriend(String friendId, String userId);

}
