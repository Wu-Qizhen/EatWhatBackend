package com.eatwhat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eatwhat.constant.MessageConstant;
import com.eatwhat.dto.UserLoginDTO;
import com.eatwhat.entity.User;
import com.eatwhat.exception.LoginFailedException;
import com.eatwhat.mapper.UserMapper;
import com.eatwhat.properties.WeChatProperties;
import com.eatwhat.service.UserService;
import com.eatwhat.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 获取微信 openid
     *
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appid", weChatProperties.getAppid());
        hashMap.put("secret", weChatProperties.getSecret());
        hashMap.put("js_code", code);
        hashMap.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, hashMap);
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getString("openid");
    }
}