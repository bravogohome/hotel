package com.example.hotel5.service;

import com.example.hotel5.entity.Roomperfect;
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
public interface RoomperfectService extends IService<Roomperfect> {

    Map<String, Object> getRestRoom(LocalDate startDate, LocalDate endDate);
}
