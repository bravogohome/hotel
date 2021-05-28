package com.example.hotel5.service.impl;

import com.example.hotel5.entity.Adminbase;
import com.example.hotel5.mapper.AdminbaseMapper;
import com.example.hotel5.service.AdminbaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
@Service
public class AdminbaseServiceImpl extends ServiceImpl<AdminbaseMapper, Adminbase> implements AdminbaseService {
    @Autowired
    AdminbaseMapper adminbaseMapper;

    @Override
    public boolean login(String adminId, String password) {
        List<Adminbase> admin= adminbaseMapper.findAdmin(adminId);
        if (admin.isEmpty()){
            return false;
        }else{
            if (!admin.get(0).getAdminPassword().equals(password)){
                return false;
            }
            return true;
        }

    }
}
