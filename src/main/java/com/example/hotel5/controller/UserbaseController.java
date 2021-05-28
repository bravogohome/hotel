package com.example.hotel5.controller;


import com.example.hotel5.entity.Userbase;
import com.example.hotel5.service.UserbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/userbase")
public class UserbaseController {
    @Autowired
    UserbaseService userbaseService;

    @ResponseBody
    @GetMapping("/loginmessage")
    public Map<String,Object> getUserLoginMessage(@RequestParam(value = "phone") String phone,@RequestParam(value = "password") String password){
        return userbaseService.login(phone,password);
    }

    @GetMapping("/login")
    public ModelAndView userLogin(@RequestParam(value = "phone") String phone,@RequestParam(value = "password") String password){
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> loginmessage=userbaseService.login(phone, password);
        switch((int)loginmessage.get("errorCode")){
            case 0:
                Userbase user=(Userbase)loginmessage.get("loginUser");
                modelAndView.setViewName("index");
                modelAndView.addObject("userId",user.getId());
                modelAndView.addObject("message",loginmessage.get("message"));
                modelAndView.addObject("loginUser", loginmessage.get("loginUser"));
                break;
            case 1:
            case 2:
            case 3:
                modelAndView.setViewName("login");
                modelAndView.addObject("message", loginmessage.get("message"));
                break;
            default:
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/registermessage")
    public Map<String,Object> getUserRegisterMessage(@RequestParam(value="phone") String phone,@RequestParam(value = "password") String password){
        return userbaseService.register(phone, password);
    }

    @GetMapping("/register")
    public ModelAndView userRegister(@RequestParam(value="phone") String phone,@RequestParam(value = "password") String password){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> registermessage=userbaseService.register(phone, password);
        switch((int)registermessage.get("errorCode")){
            case 0:
                modelAndView.setViewName("login");
                modelAndView.addObject("message",registermessage.get("message"));
                break;
            case 1:
                modelAndView.setViewName("register");
                modelAndView.addObject("message",registermessage.get("message"));
                break;
            default:
        }
        return modelAndView;
    }


}
