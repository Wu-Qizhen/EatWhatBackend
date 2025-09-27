package com.eatwhat.service;

import com.eatwhat.vo.BusinessDataVO;
import com.eatwhat.vo.DishOverViewVO;
import com.eatwhat.vo.OrderOverViewVO;
import com.eatwhat.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    /**
     * 根据时间段统计营业数据
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询菜品总览
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     */
    SetmealOverViewVO getSetmealOverView();
}