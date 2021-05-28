package com.example.hotel5.service;

import com.example.hotel5.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
public interface OrdersService extends IService<Orders> {

    Map<String, Object> disOrder(Integer userId, String roomType, String roomNum, String startDay, String endDay) ;

    Map<String, Object> makeOrders(Integer userId, String roomType, String roomNum, LocalDate startDay, LocalDate endDay);

    Map<String, Object> getUserOrdersMessage(int id);
}
