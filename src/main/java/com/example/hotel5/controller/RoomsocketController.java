package com.example.hotel5.controller;


import com.example.hotel5.entity.Roomsocket;
import com.example.hotel5.mapper.RoomsocketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gohome
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/roomsocket")
public class RoomsocketController {

    @Autowired
    RoomsocketMapper roomsocketMapper;

    @GetMapping("/roomDetail")
    public ModelAndView roomDetail(@RequestParam(value = "roomNum")String roomNum){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("roomDetail");
        modelAndView.addObject("roomMessage",roomNum+"房间历史温湿度详情");
        List<Roomsocket> historys=roomsocketMapper.findNumber(roomNum);
        for (int i=1;i<=5;i++){
            modelAndView.addObject("temperature"+i,historys.get(i-1).getRoomTemperature());
            modelAndView.addObject("noise"+i,historys.get(i-1).getRoomNoise());
            modelAndView.addObject("PM"+i,String.valueOf(historys.get(i-1).getRoomPM()));
            modelAndView.addObject("wet"+i,historys.get(i-1).getRoomWet());
        }
        return modelAndView;
    }

    @GetMapping("/roomDetailUserVersion")
    public ModelAndView roomDetailUserVersion(@RequestParam(value = "roomNum")String roomNum){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("roomDetailUserVersion");
        modelAndView.addObject("roomMessage",roomNum+"房间历史温湿度详情");
        List<Roomsocket> historys=roomsocketMapper.findNumber(roomNum);
        for (int i=1;i<=5;i++){
            modelAndView.addObject("temperature"+i,historys.get(i-1).getRoomTemperature());
            modelAndView.addObject("noise"+i,historys.get(i-1).getRoomNoise());
            modelAndView.addObject("PM"+i,String.valueOf(historys.get(i-1).getRoomPM()));
            modelAndView.addObject("wet"+i,historys.get(i-1).getRoomWet());
        }
        return modelAndView;
    }


}
