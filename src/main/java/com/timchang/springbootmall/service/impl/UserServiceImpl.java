package com.timchang.springbootmall.service.impl;

import com.timchang.springbootmall.dao.UserDao;
import com.timchang.springbootmall.dto.UserRegisterRequest;
import com.timchang.springbootmall.model.User;
import com.timchang.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest); //命名方法: Service層使用register DAO層使用createUser
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
