package com.eatwhat.task;

import com.eatwhat.entity.Orders;
import com.eatwhat.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单超时定时任务
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 定时任务，每分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeOutOrder() {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        log.info("定时处理超时订单：{}",time);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        if (orders!=null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 定时任务，每天 1 点执行一次
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        log.info("定时处理派送中的订单：{}",time);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if (orders != null && !orders.isEmpty()){
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}