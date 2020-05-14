package com.wen.friend.repository;

import com.wen.common.model.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoFriendRepository extends JpaRepository<NoFriend, String> {

    NoFriend findByUserIdAndFriendId(String userId, String friend);

}
