package com.example.hotel5.service.impl;

import com.example.hotel5.entity.Orders;
import com.example.hotel5.entity.Room;
import com.example.hotel5.entity.Roomperfect;
import com.example.hotel5.entity.Roomstandard;
import com.example.hotel5.mapper.OrdersMapper;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.mapper.RoomperfectMapper;
import com.example.hotel5.mapper.RoomstandardMapper;
import com.example.hotel5.service.RoomperfectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class RoomperfectServiceImpl extends ServiceImpl<RoomperfectMapper, Roomperfect> implements RoomperfectService {
    @Autowired
    RoomperfectMapper roomperfectMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    RoomMapper roomMapper;

    @Transactional
    public Map<String,Object> getRestRoom(LocalDate startDay, LocalDate endDay){
        Map<String,Object> result=new HashMap<>();
        List<Room> restRoom=new ArrayList<>();
        List<Roomperfect> allRoom=roomperfectMapper.findAll();
        for (int i=0;i<allRoom.size();i++){
            if(allRoom.get(i).getOrderNum()==0){
                restRoom.add(roomMapper.findRoomByNum(allRoom.get(i).getRoomNum()).get(0));
            }else{
                String roomNum=allRoom.get(i).getRoomNum();
                List<Orders> roomOrders=ordersMapper.findOrdersByRoomNum(roomNum);
                int isSuccess=0;
                for (int j=0;j<roomOrders.size();j++){
                    if (!(startDay.compareTo(roomOrders.get(j).getEndDay())>=0||endDay.compareTo(roomOrders.get(j).getStartDay())<=0)){
                        isSuccess=1;
                        break;
                    }
                }
                if (isSuccess==0){
                    restRoom.add(roomMapper.findRoomByNum(allRoom.get(i).getRoomNum()).get(0));
                }
            }
        }
        if (restRoom.size()>0){
            result.put("errorCode",0);
            result.put("message","查找成功");
            result.put("startDay",startDay.toString());
            result.put("endDay",endDay.toString());
            result.put("restRoom",restRoom);
        }else{
            result.put("errorCode",1);
            result.put("message","无空闲房间");
        }

        return result;
    }
}
