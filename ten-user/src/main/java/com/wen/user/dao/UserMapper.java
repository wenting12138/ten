package com.wen.user.dao;

import com.wen.common.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectOneById(String id);

    User findByMobileAndPassword(User user);
}
