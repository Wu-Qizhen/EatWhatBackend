package com.eatwhat.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * C 端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {

    private String code;

}