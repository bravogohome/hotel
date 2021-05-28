package com.example.hotel5.controller;


import com.example.hotel5.service.AdminbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/adminbase")
public class AdminbaseController {
    @Autowired
    AdminbaseService adminbaseService;
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "adminId")String adminId,@RequestParam(value = "password")String password){
        ModelAndView modelAndView=new ModelAndView();
        if (adminbaseService.login(adminId,password)){
            modelAndView.setViewName("redirect:/userinformation/adminUserPage");
        }else{
            modelAndView.addObject("message","登陆失败");
            modelAndView.setViewName("adminLogin");
        }
        return modelAndView;
    }
}
