package com.example.hotel5.service;

import com.example.hotel5.entity.Userinformation;
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
public interface UserinformationService extends IService<Userinformation> {

    Map<String, Object> charge(int userId, float chargeMoney);

    Map<String, Object> findUserInformation(int userId);

    Map<String, Object> updateUserInformation(int userId,String userName,String userSex,String userIDNumber);

    Map<String, Object> queryUserBySelect(int i, String param);
    Map<String, Object> queryUserBySelect(int i, String param1,String param2);
    Map<String, Object> queryUserBySelect(String param1,String param2,String param3);
}
