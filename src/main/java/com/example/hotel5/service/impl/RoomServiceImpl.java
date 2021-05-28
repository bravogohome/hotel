package com.example.hotel5.service.impl;

import com.example.hotel5.entity.Room;
import com.example.hotel5.mapper.*;
import com.example.hotel5.service.RoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    RoomMapper roomMapper;

    @Override
    public Room getRoom(String roomNum) {
        List<Room> rooms= roomMapper.findRoomByNum(roomNum);
        return rooms.get(0);
    }
}
