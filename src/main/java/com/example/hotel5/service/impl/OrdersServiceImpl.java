package com.example.hotel5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hotel5.entity.Orders;
import com.example.hotel5.mapper.OrdersMapper;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.mapper.UserbaseMapper;
import com.example.hotel5.mapper.UserinformationMapper;
import com.example.hotel5.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    UserinformationMapper userinformationMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    UserbaseMapper userbaseMapper;

    @Transactional
    public Map<String,Object> makeOrders(Integer userId, String roomType, String roomNum, LocalDate startDay, LocalDate endDay){
        Map<String,Object> result= new HashMap<>();
        List<Orders> orders=ordersMapper.findOrdersByUserId(userId);
        float userWallet=userinformationMapper.findWalletById(userId).get(0);
        double roomMoney=roomMapper.searchRoomMoney(roomNum).get(0);
        int isAuthe=userbaseMapper.userAutheStatus(userId).get(0);
        int isOrder=0;
        long allDays=startDay.until(endDay, ChronoUnit.DAYS);
        for (int i=0;i<orders.size();i++){
            if (!(endDay.compareTo(orders.get(i).getStartDay())<0||startDay.compareTo(orders.get(i).getEndDay())>0)){
                isOrder=1;
                break;
            }
        }
        if (isOrder==1){
            result.put("errorCode",4);
            result.put("message","该用户在该时间段已有预约");
        }else{
            switch(isAuthe){
                case 0:
                    result.put("errorCode",3);
                    result.put("message","用户未实名认证");
                    break;
                case 1:
                    if ((double)userWallet<roomMoney*(0.2+allDays)){
                        result.put("errorCode",2);
                        result.put("message","用户账号余额不足");
                    }else{
                        try{
                            Orders newOrder=new Orders();
                            newOrder.setUserId(userId);
                            newOrder.setRoomType(roomType);
                            newOrder.setRoomNum(roomNum);
                            newOrder.setEndDay(endDay);
                            newOrder.setStartDay(startDay);
                            save(newOrder);
                            float newWallet=(float) (userWallet-roomMoney*(0.2+allDays));
                            userinformationMapper.changeWallet(userId,newWallet);
                            result.put("errorCode",0);
                            result.put("message","预约成功");
                        }catch (Exception e){
                            e.printStackTrace();
                            result.put("errorCode",1);
                            result.put("message","预约失败");
                        }
                    }
                    break;
                default:
            }
        }

        return result;
    }

    @Transactional
    public Map<String, Object> getUserOrdersMessage(int id) {
        Map<String,Object> userOrdersMessage=new HashMap<>();
        List<Orders> ordersList=ordersMapper.findOrdersByUserId(id);
        if (!ordersList.isEmpty()){
            userOrdersMessage.put("errorCode",0);
            userOrdersMessage.put("message","查询订单成功");
            userOrdersMessage.put("ordersList",ordersList);
        }else{
            userOrdersMessage.put("errorCode",1);
            userOrdersMessage.put("message","该用户没有订单");
        }
        return userOrdersMessage;
    }

    @Transactional
    public Map<String, Object> disOrder(Integer userId, String roomType, String roomNum, String startDay, String endDay) {
        Map<String,Object> disOrderMessage=new HashMap<>();
        try{
            ordersMapper.deleteOrders(userId,roomType,roomNum,startDay,endDay);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDay, fmt);
            LocalDate endDate=LocalDate.parse(endDay,fmt);
            long allDays=startDate.until(endDate, ChronoUnit.DAYS);
            LocalDate today=LocalDate.now();
            if (startDate.compareTo(today)>=0){
                double roomMoney=roomMapper.searchRoomMoney(roomNum).get(0);
                float userWallet=userinformationMapper.findWalletById(userId).get(0);
                float newWallet=(float) roomMoney*allDays+userWallet;
                userinformationMapper.changeWallet(userId,newWallet);
                disOrderMessage.put("errorCode",0);
                disOrderMessage.put("message","提前退订扣除毁约金");
            }else{
                double roomMoney=roomMapper.searchRoomMoney(roomNum).get(0);
                float userWallet=userinformationMapper.findWalletById(userId).get(0);
                float newWallet=(float) (roomMoney*0.2+userWallet);
                userinformationMapper.changeWallet(userId,newWallet);
                disOrderMessage.put("errorCode",0);
                disOrderMessage.put("message","退订成功返还保证金");
            }

        }catch (Exception e){
            e.printStackTrace();
            disOrderMessage.put("errorCode",1);
            disOrderMessage.put("message","退订失败");
        }
        return disOrderMessage;
    }
}
