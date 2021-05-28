package com.example.hotel5.controller;

import com.example.hotel5.entity.Userbase;
import com.example.hotel5.entity.Userinformation;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.mapper.UserbaseMapper;
import com.example.hotel5.mapper.UserinformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/trans")
public class TransController {
    @GetMapping("/toDetails")
    public ModelAndView toDetails(@RequestParam(value = "num")int num,@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        switch (num){
            case 1:
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("details1");
                break;
            case 2:
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("details2");
                break;
            case 3:
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("details3");
                break;
            default:
        }

        return modelAndView;
    }
    @GetMapping("/toIndex")
    public ModelAndView toIndex(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping("/toRoom")
    public ModelAndView toRoom(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("rooms");
        return modelAndView;
    }
    @GetMapping("/toContact")
    public ModelAndView toContact(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("contact");
        return modelAndView;
    }

    @Autowired
    UserinformationMapper userinformationMapper;
    @Autowired
    UserbaseMapper userbaseMapper;
    @GetMapping("/toUserChange")
    public ModelAndView toUserChange(@RequestParam(value = "userId")int userId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userId",userId);
        Userinformation changeUser= userinformationMapper.getUserInformationById(userId).get(0);
        modelAndView.addObject("changeUser",changeUser);
        Userbase userbase=userbaseMapper.findUserbaseById(userId).get(0);
        modelAndView.addObject("phone",userbase.getPhone());
        modelAndView.setViewName("manageUserChange");
        return modelAndView;
    }

}