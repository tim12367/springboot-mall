package com.timchang.springbootmall.dao;

import com.timchang.springbootmall.dto.UserRegisterRequest;
import com.timchang.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
