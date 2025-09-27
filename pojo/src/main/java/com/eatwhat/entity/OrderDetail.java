package com.eatwhat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 名称
    private String name;

    // 订单
    private Long orderId;

    // 菜品
    private Long dishId;

    // 套餐
    private Long setmealId;

    // 口味
    private String dishFlavor;

    // 数量
    private Integer number;

    // 金额
    private BigDecimal amount;

    // 图片
    private String image;
}