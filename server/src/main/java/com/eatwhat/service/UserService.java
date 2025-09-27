package com.eatwhat.service;

import com.eatwhat.dto.UserLoginDTO;
import com.eatwhat.entity.User;

public interface UserService {
    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}