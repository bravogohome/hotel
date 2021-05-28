package com.example.hotel5.service;

import com.example.hotel5.entity.Adminbase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
public interface AdminbaseService extends IService<Adminbase> {

    boolean login(String adminId, String password);
}
