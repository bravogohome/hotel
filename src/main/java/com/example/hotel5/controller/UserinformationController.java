package com.example.hotel5.controller;


import com.example.hotel5.entity.Userbase;
import com.example.hotel5.entity.Userinformation;
import com.example.hotel5.mapper.UserbaseMapper;
import com.example.hotel5.mapper.UserinformationMapper;
import com.example.hotel5.service.UserinformationService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
@Controller
@RequestMapping("/userinformation")
public class UserinformationController {
    @Autowired
    UserinformationService userinformationService;

    @GetMapping("/charge")
    public ModelAndView charge(@RequestParam(value = "userId")int userId,@RequestParam(value = "chargeMoney")float chargeMoney){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> chargeMessage=userinformationService.charge(userId,chargeMoney);
        switch ((int)chargeMessage.get("errorCode")){
            case 0:
                Map<String,Object> userInformation=userinformationService.findUserInformation(userId);
                modelAndView.setViewName("person");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("userbase",userInformation.get("userbase"));
                modelAndView.addObject("user",userInformation.get("user"));
                modelAndView.addObject("chargemessage",chargeMessage.get("message"));
                break;
            case 1:
                Map<String,Object> userInformation1=userinformationService.findUserInformation(userId);
                modelAndView.setViewName("person");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("userbase",userInformation1.get("userbase"));
                modelAndView.addObject("user",userInformation1.get("user"));
                modelAndView.addObject("chargemessage",chargeMessage.get("message"));
                break;
            default:
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/chargeMessage")
    public Map<String,Object> getChargeMessage(@RequestParam(value = "userId")int userId, @RequestParam(value = "chargeMoney")float chargeMoney){
        return userinformationService.charge(userId,chargeMoney);
    }

    @ResponseBody
    @GetMapping("/getUserInformationMessage")
    public Map<String,Object> getUserInformationMessage(@RequestParam(value = "userId")int userId){
        return userinformationService.findUserInformation(userId);
    }

    @GetMapping("/getUserInformation")
    public ModelAndView getUserInformation(@RequestParam(value = "userId")int userId,String makeordermessage,String updatemessage){
        Map<String,Object> userInformation=userinformationService.findUserInformation(userId);
        ModelAndView modelAndView=new ModelAndView();
        switch ((int)userInformation.get("errorCode")){
            case 0:
                modelAndView.setViewName("person");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("makeordermessage",makeordermessage);
                modelAndView.addObject("updatemessage",updatemessage);
                modelAndView.addObject("userbase",userInformation.get("userbase"));
                modelAndView.addObject("user",userInformation.get("user"));
                break;
            default:
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/getUpdateUserInformationMessage")
    public Map<String,Object> getUpdateUserInformationMessage(@RequestParam(value = "userId")int userId,@RequestParam(value = "userName")String userName,
                                                              @RequestParam(value = "userSex")String userSex,@RequestParam(value = "userIDNumber")String userIDNumber){
        return userinformationService.updateUserInformation(userId,userName,userSex,userIDNumber);
    }

    @GetMapping("/updateUserInformation")
    public ModelAndView updateUserInformation(@RequestParam(value = "userId")int userId,@RequestParam(value = "userName")String userName,
                                              @RequestParam(value = "userSex")String userSex,@RequestParam(value = "userIDNumber")String userIDNumber){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> updateMessage=getUpdateUserInformationMessage(userId,userName,userSex,userIDNumber);
        switch ((int)updateMessage.get("errorCode")){
            case 0:
                modelAndView.setViewName("redirect:/userinformation/getUserInformation");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("updatemessage",updateMessage.get("message"));
                break;
            case 1:
                modelAndView.setViewName("error");
                break;
        }
        return modelAndView;
    }

    @GetMapping("/toUserWallet")
    public ModelAndView toUserWallet(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> userInformation=userinformationService.findUserInformation(userId);
        switch((int)userInformation.get("errorCode")){
            case 0:
                modelAndView.setViewName("charge");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("user",userInformation.get("user"));
                break;
            case 1:
                break;
            default:
        }
        return modelAndView;
    }

    @Autowired
    UserinformationMapper userinformationMapper;
    @Autowired
    UserbaseMapper userbaseMapper;

    @GetMapping("/adminUserPage")
    public ModelAndView adminUserPage(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageUser");
        List<Userinformation> allUserInformation=userinformationMapper.findAll();
        List<Userbase> allUserBase=userbaseMapper.findAll();
        modelAndView.addObject("userInformations",allUserInformation);
        List<String> phones=new ArrayList<>();
        List<String> blackMessage=new ArrayList<>();
        List<String> isBlacks=new ArrayList<>();
        for (int i=0;i<allUserBase.size();i++){
            phones.add(allUserBase.get(i).getPhone());
            if (allUserBase.get(i).getIsBlack()!=0){
                blackMessage.add("移出黑名单");
                isBlacks.add("是");
            }else{
                blackMessage.add("加入黑名单");
                isBlacks.add("否");
            }
        }
        modelAndView.addObject("isBlacks",isBlacks);
        modelAndView.addObject("blackMessage",blackMessage);
        modelAndView.addObject("userBases",allUserBase);
        modelAndView.addObject("phones",phones);
        return modelAndView;
    }

    @GetMapping("/black")
    public ModelAndView black(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        int isBlack=userbaseMapper.findUserbaseById(userId).get(0).getIsBlack();
        if (isBlack==0){
            userinformationMapper.addBlack(userId);
        }else{
            userinformationMapper.removeBlack(userId);
        }
        modelAndView.setViewName("redirect:/userinformation/adminUserPage");
        return modelAndView;
    }

    @GetMapping("/adminChangeUserInformation")
    public ModelAndView adminChangeUserInformation(@RequestParam(value = "userId")int userId,String phone,String userName,String userSex,String userIDNumber){
        ModelAndView modelAndView=new ModelAndView();
        try{
            userbaseMapper.updatePhone(userId,phone);
            userinformationMapper.updateAuthe(userId,userName,userSex,userIDNumber);
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:/userinformation/adminUserPage");
        return modelAndView;
    }

    @GetMapping("/query")
    public ModelAndView query(String userName,String phone,String userIDNumber){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> queryMap=new HashMap<>();
        modelAndView.setViewName("manageUser");
        if (userName.isEmpty()&&phone.isEmpty()&&userIDNumber.isEmpty()){
            modelAndView.setViewName("redirect:/userinformation/adminUserPage");
            return modelAndView;
        }else if(userName.isEmpty()&&phone.isEmpty()&&!userIDNumber.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(1,userIDNumber);
        }else if(userName.isEmpty()&&!phone.isEmpty()&&userIDNumber.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(2,phone);
        }else if (phone.isEmpty()&&!userName.isEmpty()&&userIDNumber.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(3,userName);
        }else if (userName.isEmpty()&&!phone.isEmpty()&&!userIDNumber.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(1,phone,userIDNumber);
        }else if (phone.isEmpty()&&!userName.isEmpty()&&!userIDNumber.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(2,userName,userIDNumber);
        }else if (userIDNumber.isEmpty()&&!userName.isEmpty()&&!phone.isEmpty()){
            queryMap=userinformationService.queryUserBySelect(3,phone,userName);
        }else{
            queryMap=userinformationService.queryUserBySelect(userName,phone,userIDNumber);
        }
        switch((int)queryMap.get("errorCode")){
            case 0:
                List<Userinformation> allUserInformation=(List<Userinformation>) queryMap.get("userinformation");
                List<String> phones= (List<String>) queryMap.get("phones");
                List<Userbase> allUserBase=new ArrayList<>();
                for (int i=0;i<phones.size();i++){
                    Userbase user=userbaseMapper.selectByPhone(phones.get(0)).get(0);
                    allUserBase.add(user);
                }
                modelAndView.addObject("userInformations",allUserInformation);
                List<String> blackMessage=new ArrayList<>();
                List<String> isBlacks=new ArrayList<>();
                for (int i=0;i<allUserBase.size();i++){
                    phones.add(allUserBase.get(i).getPhone());
                    if (allUserBase.get(i).getIsBlack()!=0){
                        blackMessage.add("移出黑名单");
                        isBlacks.add("是");
                    }else{
                        blackMessage.add("加入黑名单");
                        isBlacks.add("否");
                    }
                }
                modelAndView.addObject("isBlacks",isBlacks);
                modelAndView.addObject("blackMessage",blackMessage);
                modelAndView.addObject("userBases",allUserBase);
                modelAndView.addObject("phones",phones);
                break;
            case 1:
                modelAndView.addObject("queryMessage",queryMap.get("queryMessage"));
                break;
            default:
        }
        return modelAndView;
    }

}
