package com.eatwhat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    // 日期
    private String dateList;

    // 每日订单数
    private String orderCountList;

    // 每日有效订单数
    private String validOrderCountList;

    // 订单总数
    private Integer totalOrderCount;

    // 有效订单数
    private Integer validOrderCount;

    // 订单完成率
    private Double orderCompletionRate;

}