package com.eatwhat.mapper;

import com.eatwhat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据 openid 查询用户
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入用户数据
     */
    void insert(User user);

    /**
     * 根据 ID 查询用户
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 根据条件统计用户数量
     */
    Integer countByMap(Map<String, Object> map);
}