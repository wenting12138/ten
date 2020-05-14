package com.wen.user.dao;

import com.wen.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
	User findByMobile(String mobile);

	@Modifying
	@Query(value = "update tb_user set fanscount = fanscount + ? where id = ?", nativeQuery = true)
    void updateFanscount(Integer num, String friendId);

    void updateFollowcount(Integer num, String userId);
}
