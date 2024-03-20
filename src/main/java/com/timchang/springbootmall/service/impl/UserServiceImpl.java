package com.timchang.springbootmall.service.impl;

import com.timchang.springbootmall.dao.UserDao;
import com.timchang.springbootmall.dto.UserLoginRequest;
import com.timchang.springbootmall.dto.UserRegisterRequest;
import com.timchang.springbootmall.model.User;
import com.timchang.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // 檢查是否重複註冊
        if (user != null) {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail()); // log可以用大括號插入
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "重複註冊");
        }

        // 命名方法: Service層使用register DAO層使用createUser 因為Service層 還有做其他判斷
        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null) {
            log.warn("該email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email商未註冊!");
        }

        if (user.getPassword().equals(userLoginRequest.getPassword())) {
            return user;
        } else {
            log.warn("email {} 的密碼不正確!", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密碼不正確!");
        }
    }
}
