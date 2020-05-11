package com.wen.user.service;

import com.wen.common.model.User;
import com.wen.common.result.ResultService;

public interface UserService {

    /**
     *  查询用户
     * @param userId
     * @return
     */
    ResultService<User> findOneUser(String userId);

    /**
     *  登录
     * @param user
     * @return
     */
    ResultService<User> login(User user);
}
