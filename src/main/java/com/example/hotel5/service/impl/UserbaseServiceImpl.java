package com.example.hotel5.service.impl;

import com.example.hotel5.Hotel5Application;
import com.example.hotel5.entity.Userbase;
import com.example.hotel5.mapper.UserbaseMapper;
import com.example.hotel5.service.UserbaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserbaseServiceImpl extends ServiceImpl<UserbaseMapper, Userbase> implements UserbaseService {
    @Autowired
    UserbaseMapper userbaseMapper;

    @Transactional
    public Map<String,Object> register(String phone,String password){
        Map<String,Object> registerResult=new HashMap<>();
        List<Userbase> registerUser=new ArrayList<>();
        registerUser=userbaseMapper.selectByPhone(phone);
        if (!registerUser.isEmpty()){
            registerResult.put("errorCode",1);
            registerResult.put("message","该用户已被注册");
        }else{
            Userbase newUser=new Userbase();
            List<Userbase> largestIdUser=new ArrayList<>();
            largestIdUser=userbaseMapper.getLargestId();
            if (largestIdUser.isEmpty()){
                newUser.setId(1);
            }else{
                newUser.setId(largestIdUser.get(0).getId()+1);
            }
            newUser.setPassword(password);
            newUser.setPhone(phone);
            newUser.setIsAuthe(0);
            newUser.setIsBlack(0);
            save(newUser);             //插入数据库
            registerResult.put("errorCode",0);
            registerResult.put("message","注册成功");
            registerResult.put("user",newUser);
        }
        return registerResult;
    }

    @Transactional
    public Map<String,Object> login(String phone, String password){
        Map<String,Object> loginResult=new HashMap<>();
        List<Userbase> loginUser=new ArrayList<>();
        loginUser=userbaseMapper.selectByPhone(phone);
        if (loginUser.isEmpty()){
            loginResult.put("errorCode",1);
            loginResult.put("message","该用户不存在");
        }else{
                if (loginUser.get(0).getPassword().equals(password)){
                    if(loginUser.get(0).getIsBlack()==1){
                        loginResult.put("errorCode",3);
                        loginResult.put("message","该用户为黑名单");
                    }else{
                        loginResult.put("errorCode",0);
                        loginResult.put("message","登陆成功");
                        loginResult.put("loginUser",loginUser.get(0));
                    }
                }else{
                    loginResult.put("errorCode",2);
                    loginResult.put("message","账号密码错误");
                }
        }
        return loginResult;
    }


}
