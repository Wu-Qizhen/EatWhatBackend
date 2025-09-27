package com.eatwhat.service;

import com.eatwhat.vo.OrderReportVO;
import com.eatwhat.vo.SalesTop10ReportVO;
import com.eatwhat.vo.TurnoverReportVO;
import com.eatwhat.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计
     */
    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量排名
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    /**
     * 导出运营数据报表
     */
    void exportBusinessData(HttpServletResponse response);
}