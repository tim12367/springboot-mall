package com.timchang.springbootmall.service;

import com.timchang.springbootmall.dto.UserRegisterRequest;
import com.timchang.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
