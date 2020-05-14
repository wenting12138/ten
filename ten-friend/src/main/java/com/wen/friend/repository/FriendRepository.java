package com.wen.friend.repository;

import com.wen.common.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, String> {

    Friend findByUserIdAndFriendId(String userId, String friend);

    @Modifying
    @Query(value = "update tb_friend set islike = ? where userid = ? and friendid = ?", nativeQuery = true)
    void updateIsLike(String isLike, String userId, String friendId);

    @Modifying
    @Query(value = "delete from tb_friend where userid = ? and friendid = ?", nativeQuery = true)
    void deleteFriend(String userId, String friendId);

}
