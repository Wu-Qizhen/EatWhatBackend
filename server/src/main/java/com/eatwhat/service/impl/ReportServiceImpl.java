package com.eatwhat.service.impl;

import com.eatwhat.dto.GoodsSalesDTO;
import com.eatwhat.entity.Orders;
import com.eatwhat.mapper.OrderMapper;
import com.eatwhat.mapper.UserMapper;
import com.eatwhat.service.ReportService;
import com.eatwhat.service.WorkspaceService;
import com.eatwhat.vo.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 营业额统计
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getLocalDates(begin, end);

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            if (turnover == null) {
                turnover = 0.0;
            }
            turnoverList.add(turnover);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getLocalDates(begin, end);

        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("end", endTime);
            Integer totalUser = userMapper.countByMap(map);

            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }
        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 订单统计
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getLocalDates(begin, end);

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate date : dateList) {
            orderCountList.add(getOrderCount(date, null));
            validOrderCountList.add(getOrderCount(date, Orders.COMPLETED));
        }

        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).orElse(0);
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).orElse(0);

        double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> top10 = orderMapper.getSalesTop10(beginTime, endTime);
        List<String> names = top10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numbers = top10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");
        String numberList = StringUtils.join(numbers, ",");

        return SalesTop10ReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    @Override
    public void exportBusinessData(HttpServletResponse response) {
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);

        BusinessDataVO businessData = workspaceService.getBusinessData(
                LocalDateTime.of(dateBegin, LocalTime.MIN),
                LocalDateTime.of(dateEnd, LocalTime.MAX)
        );

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表.xlsx");

        try {
            assert inputStream != null;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("运营数据报表");

            XSSFRow row = sheet.getRow(2);
            row.getCell(1).setCellValue(businessData.getTurnover());
            row.getCell(3).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(5).setCellValue(businessData.getNewUsers());

            row = sheet.getRow(3);
            row.getCell(1).setCellValue(businessData.getValidOrderCount());
            row.getCell(3).setCellValue(businessData.getUnitPrice());

            // 填充详细数据
            for (int i = 0; i < 30; i++) {
                LocalDate date = dateBegin.plusDays(i);
                BusinessDataVO data = workspaceService.getBusinessData(
                        LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX)
                );

                row = sheet.getRow(6 + i);
                if (row != null) {
                    row.getCell(0).setCellValue(date.toString());
                    row.getCell(1).setCellValue(data.getTurnover());
                    row.getCell(2).setCellValue(data.getValidOrderCount());
                    row.getCell(3).setCellValue(data.getOrderCompletionRate());
                    row.getCell(4).setCellValue(data.getUnitPrice());
                    row.getCell(5).setCellValue(data.getNewUsers());
                }
            }

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);

            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            log.error(e.toString());
            // e.printStackTrace();
        }
    }

    /**
     * 根据条件统计订单数量
     */
    private Integer getOrderCount(LocalDate date, Integer status) {
        LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", status);
        return orderMapper.countByMap(map);
    }

    /**
     * 获取两个日期之间的所有日期
     */
    @NotNull
    private List<LocalDate> getLocalDates(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }
}