package com.example.hotel5.service.impl;

import com.example.hotel5.entity.Userbase;
import com.example.hotel5.entity.Userinformation;
import com.example.hotel5.mapper.UserbaseMapper;
import com.example.hotel5.mapper.UserinformationMapper;
import com.example.hotel5.service.UserinformationService;
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
public class UserinformationServiceImpl extends ServiceImpl<UserinformationMapper, Userinformation> implements UserinformationService {

    @Autowired
    UserinformationMapper userinformationMapper;
    @Autowired
    UserbaseMapper userbaseMapper;

    @Transactional
    public Map<String, Object> charge(int userId, float chargeMoney) {
        Map<String,Object> chargeMessage=new HashMap<>();
        try{
            float newWallet=chargeMoney+userinformationMapper.findWalletById(userId).get(0);
            userinformationMapper.changeWallet(userId,newWallet);
            chargeMessage.put("errorCode",0);
            chargeMessage.put("message","充值成功");
            chargeMessage.put("userWallet",newWallet);
        }catch (Exception e){
            e.printStackTrace();
            chargeMessage.put("errorCode",1);
            chargeMessage.put("message","充值失败");
        }
        return chargeMessage;
    }

    @Transactional
    public Map<String, Object> findUserInformation(int userId) {
        Map<String,Object> userInformation=new HashMap<>();
        Userinformation user=(Userinformation) userinformationMapper.getUserInformationById(userId).get(0);
        Userbase userbase=userbaseMapper.findUserbaseById(userId).get(0);
        userInformation.put("errorCode",0);
        userInformation.put("message","查找成功");
        userInformation.put("user",user);
        userInformation.put("userbase",userbase);
        return userInformation;
    }

    @Transactional
    public Map<String, Object> updateUserInformation(int userId, String userName, String userSex, String userIDNumber) {
        Map<String,Object> updateUserInformationMessage=new HashMap<>();
        if (!(userSex.equals("男")||userSex.equals("女"))){
            updateUserInformationMessage.put("errorCode",2);
            updateUserInformationMessage.put("message","非法性别");
        }else if (userIDNumber.length()!=18){
            updateUserInformationMessage.put("errorCode",3);
            updateUserInformationMessage.put("message","身份证非法");
        }
        try{
            userinformationMapper.updateAuthe(userId,userName,userSex,userIDNumber);
            updateUserInformationMessage.put("errorCode",0);
            updateUserInformationMessage.put("message","更新成功");
        }catch (Exception e){
            updateUserInformationMessage.put("errorCode",1);
            updateUserInformationMessage.put("message","更新失败");
            e.printStackTrace();
        }
        return updateUserInformationMessage;
    }

    @Override
    public Map<String, Object> queryUserBySelect(int i, String param) {
        Map<String,Object> queryResult=new HashMap<>();
        switch(i){
            case 1:
                List<Userinformation> users=userinformationMapper.findUserByUserIDNumber(param);
                if (users.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    queryResult.put("errorCode",0);
                    queryResult.put("userinformation",users);
                    List<String> phones=new ArrayList<>();
                    for (int j=0;j<users.size();j++){
                        phones.add(userbaseMapper.findUserbaseById(users.get(j).getUserId()).get(0).getPhone());
                    }
                   queryResult.put("phones",phones);
                }
                break;
            case 2:
                List<Userbase> users2=userbaseMapper.selectByPhone(param);
                if (users2.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    List<Userinformation> userinformations=new ArrayList<>();
                    List<String> phones=new ArrayList<>();
                    queryResult.put("errorCode",0);
                    for (int j=0;j<users2.size();j++){
                        userinformations.add(userinformationMapper.getUserInformationById(users2.get(j).getId()).get(0));
                        phones.add(userbaseMapper.findUserbaseById(users2.get(j).getId()).get(0).getPhone());
                    }
                    queryResult.put("userinformation",userinformations);
                    queryResult.put("phones",phones);
                }
                break;
            case 3:
                List<Userinformation> users1=userinformationMapper.findUserByUserName(param);
                if (users1.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    queryResult.put("errorCode",0);
                    queryResult.put("userinformation",users1);
                    List<String> phones=new ArrayList<>();
                    for (int j=0;j<users1.size();j++){
                        phones.add(userbaseMapper.findUserbaseById(users1.get(j).getUserId()).get(0).getPhone());
                    }
                    queryResult.put("phones",phones);
                }
                break;
            default:
        }
        return queryResult;
    }

    @Override
    public Map<String, Object> queryUserBySelect(int i, String param1, String param2) {
        Map<String,Object> queryResult=new HashMap<>();
        switch(i){
            case 1:
                List<Userbase> users2=userbaseMapper.selectByPhone(param1);
                if (users2.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    List<Userinformation> userinformations=new ArrayList<>();
                    List<String> phones=new ArrayList<>();
                    int errorCode=1;
                    for (int j=0;j<users2.size();j++){
                        Userinformation user=userinformationMapper.getUserInformationById(users2.get(j).getId()).get(0);
                        if (user.getUserIDNumber().equals(param2)){
                            userinformations.add(user);
                            phones.add(users2.get(j).getPhone());
                            errorCode=0;
                        }
                    }
                    switch (errorCode){
                        case 0:
                            queryResult.put("errorCode",0);
                            queryResult.put("userinformation",userinformations);
                            queryResult.put("phones",phones);
                            break;
                        case 1:
                            queryResult.put("errorCode",1);
                            queryResult.put("queryMessage","无该用户，请重新查询");
                            break;
                    }
                }
                break;
            case 2:
                List<Userinformation> users=userinformationMapper.findUserByUserNameAndUserIDNumber(param1,param2);
                if (users.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    queryResult.put("errorCode",0);
                    queryResult.put("userinformation",users);
                    List<String> phones=new ArrayList<>();
                    for (int j=0;j<users.size();j++){
                        phones.add(userbaseMapper.findUserbaseById(users.get(j).getUserId()).get(0).getPhone());
                    }
                    queryResult.put("phones",phones);
                }
                break;
            case 3:
                List<Userbase> users1=userbaseMapper.selectByPhone(param1);
                if (users1.isEmpty()){
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                }else{
                    List<Userinformation> userinformations=new ArrayList<>();
                    List<String> phones=new ArrayList<>();
                    int errorCode=1;
                    for (int j=0;j<users1.size();j++){
                        Userinformation user=userinformationMapper.getUserInformationById(users1.get(j).getId()).get(0);
                        if (user.getUserName().equals(param2)){
                            userinformations.add(user);
                            phones.add(users1.get(j).getPhone());
                            errorCode=0;
                        }
                    }
                    switch (errorCode){
                        case 0:
                            queryResult.put("errorCode",0);
                            queryResult.put("userinformation",userinformations);
                            queryResult.put("phones",phones);
                            break;
                        case 1:
                            queryResult.put("errorCode",1);
                            queryResult.put("queryMessage","无该用户，请重新查询");
                            break;
                    }
                }
                break;
        }
        return queryResult;
    }

    @Override
    public Map<String, Object> queryUserBySelect(String param1, String param2, String param3) {
        Map<String,Object> queryResult=new HashMap<>();
        List<Userbase> userbases=userbaseMapper.selectByPhone(param2);
        if (userbases.isEmpty()){
            queryResult.put("errorCode",1);
            queryResult.put("queryMessage","无该用户，请重新查询");
        }else{
            List<Userinformation> userinformations=new ArrayList<>();
            List<String> phones=new ArrayList<>();
            int errorCode=1;
            for (int i=0;i<userbases.size();i++){
                Userinformation user=userinformationMapper.getUserInformationById(userbases.get(i).getId()).get(0);
                if (user.getUserName().equals(param1)&&user.getUserIDNumber().equals(param3)){
                    userinformations.add(user);
                    phones.add(userbases.get(i).getPhone());
                    errorCode=0;
                }
            }
            switch (errorCode){
                case 0:
                    queryResult.put("errorCode",0);
                    queryResult.put("userinformation",userinformations);
                    queryResult.put("phones",phones);
                    break;
                case 1:
                    queryResult.put("errorCode",1);
                    queryResult.put("queryMessage","无该用户，请重新查询");
                    break;
                default:
            }
        }
        return queryResult;
    }
}
