package com.example.hotel5.service;

import com.example.hotel5.entity.Userbase;
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
public interface UserbaseService extends IService<Userbase> {
    Map<String,Object> login(String phone, String password);

    Map<String, Object> register(String phone, String password);
}
