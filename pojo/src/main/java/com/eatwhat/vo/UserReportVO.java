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
public class UserReportVO implements Serializable {

    // 日期
    private String dateList;

    // 用户总量
    private String totalUserList;

    // 新增用户
    private String newUserList;

}