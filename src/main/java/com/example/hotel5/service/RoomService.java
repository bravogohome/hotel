package com.example.hotel5.service;

import com.example.hotel5.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
public interface RoomService extends IService<Room> {
     Room getRoom(String roomNum);
}
