package com.eatwhat.controller.admin;

import com.eatwhat.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    public static final String KEY_SHOP_STATUS = "SHOP_STATUS";
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 设置店铺营业状态
     *
     * @param status
     * @return
     */
    @ApiOperation("设置店铺营业状态")
    @PutMapping("/{status}")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY_SHOP_STATUS, status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     *
     * @return
     */
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY_SHOP_STATUS);
        log.info("获取店铺营业状态：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}