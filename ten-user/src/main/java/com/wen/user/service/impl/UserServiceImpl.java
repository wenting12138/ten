package com.wen.user.service.impl;

import com.wen.common.model.User;
import com.wen.common.result.ResultService;
import com.wen.user.dao.UserMapper;
import com.wen.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultService<User> findOneUser(String userId) {
        User user = userMapper.selectOneById(userId);
        if (user == null) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, 1, user);
    }

    @Override
    public ResultService<User> login(User user) {
        User result = userMapper.findByMobileAndPassword(user);
        if (result == null) {
            return new ResultService<>(false);
        }
        result.setPassword(null);
        return new ResultService<>(true, 1, result);
    }
}
