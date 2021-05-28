package com.example.hotel5.service;

import com.example.hotel5.entity.Roomsocket;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gohome
 * @since 2020-12-28
 */
public interface RoomsocketService extends IService<Roomsocket> {

    void updateAllRoom();
}
