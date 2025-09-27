package com.eatwhat.controller.user;

import com.eatwhat.constant.JwtClaimsConstant;
import com.eatwhat.dto.UserLoginDTO;
import com.eatwhat.entity.User;
import com.eatwhat.properties.JwtProperties;
import com.eatwhat.result.Result;
import com.eatwhat.service.UserService;
import com.eatwhat.utils.JwtUtil;
import com.eatwhat.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录，用户信息：{}", userLoginDTO.getCode());
        User user = userService.wxLogin(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}