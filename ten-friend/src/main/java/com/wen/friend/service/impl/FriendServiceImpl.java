package com.wen.friend.service.impl;

import com.wen.common.constant.FriendConstant;
import com.wen.common.model.Friend;
import com.wen.common.model.NoFriend;
import com.wen.common.result.ResultService;
import com.wen.friend.client.UserClient;
import com.wen.friend.repository.FriendRepository;
import com.wen.friend.repository.NoFriendRepository;
import com.wen.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private NoFriendRepository noFriendRepository;

    @Autowired
    private UserClient userClient;

    @Override
    @Transactional
    public ResultService<Void> addFriend(String friendId, String userId) {
        // 先判断是否存在
        Friend friend = friendRepository.findByUserIdAndFriendId(userId, friendId);
        if (friend != null) {
            return new ResultService<>(false);
        }
        // userid 添加 friendid 为好友
        friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setIsLike(FriendConstant.unlike);
        friendRepository.save(friend);

        friend = null;
        // 查看friendid 和 userid 的关系
        friend = friendRepository.findByUserIdAndFriendId(friendId, userId);
        if (friend != null) {
            // 把双方的islike 都改为1;
            friendRepository.updateIsLike(FriendConstant.like, userId, friendId);
            friendRepository.updateIsLike(FriendConstant.like, friendId, userId);
        }

        // 调用 用户服务, 将 friend 的 粉丝数 +1,  user的关注数 +1
        userClient.updateFanscountFollowcount(userId, friendId, 1);

        return new ResultService(true);
    }

    @Override
    @Transactional
    public ResultService<Void> addNoFriend(String friendId, String userId) {

        // 先查询是否存在
        NoFriend noFriend = noFriendRepository.findByUserIdAndFriendId(userId, friendId);
        if (noFriend != null) {
            return new ResultService(false);
        }
        noFriend = new NoFriend();
        noFriend.setUserId(userId);
        noFriend.setFriendId(friendId);
        noFriendRepository.save(noFriend);

        return new ResultService<>(true);
    }

    @Override
    @Transactional
    public void deleteFriend(String friendId, String userId) {
        // 删除userid  和 friendid
        friendRepository.deleteFriend(userId, friendId);

        // 将 friendId  userid 的 islike 设置为0
        friendRepository.updateIsLike(FriendConstant.unlike, friendId, userId);
        // 添加 nofriend
        NoFriend noFriend = new NoFriend();
        noFriend.setUserId(userId);
        noFriend.setFriendId(friendId);
        noFriendRepository.save(noFriend);


        // 调用 用户服务, 将 friend 的 粉丝数 -1,  user的关注数 -1
        userClient.updateFanscountFollowcount(userId, friendId, -1);
    }
}
