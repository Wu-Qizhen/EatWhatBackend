package com.eatwhat.dto;

import com.eatwhat.entity.OrderDetail;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    // 订单号
    private String number;

    // 订单状态
    private Integer status;

    // 下单用户
    private Long userId;

    // 地址
    private Long addressBookId;

    // 下单时间
    private LocalDateTime orderTime;

    // 结账时间
    private LocalDateTime checkoutTime;

    // 支付方式
    private Integer payMethod;

    // 实收金额
    private BigDecimal amount;

    // 备注
    private String remark;

    // 用户名
    private String userName;

    // 手机号
    private String phone;

    // 地址
    private String address;

    // 收货人
    private String consignee;

    private List<OrderDetail> orderDetails;

}