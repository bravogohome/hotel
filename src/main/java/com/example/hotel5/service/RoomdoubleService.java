package com.example.hotel5.service;

import com.example.hotel5.entity.Roomdouble;
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
public interface RoomdoubleService extends IService<Roomdouble> {
    Map<String, Object> getRestRoom(LocalDate startDay, LocalDate endDay);
}
