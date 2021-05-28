package com.example.hotel5.controller;


import com.example.hotel5.entity.Room;
import com.example.hotel5.entity.Roomperfect;
import com.example.hotel5.service.RoomperfectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RestController
@RequestMapping("/roomperfect")
public class RoomperfectController {
    @Autowired
    RoomperfectService roomperfectService;

    @GetMapping("/getRestRoom")
    public ModelAndView getRestRoom(ModelAndView modelAndView, String startDay, String endDay,String userId){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDay, fmt);
        LocalDate endDate=LocalDate.parse(endDay);
        Map<String,Object> restRoomMap=roomperfectService.getRestRoom(startDate,endDate);
        List<Room> restRoomList=(List<Room>) restRoomMap.get("restRoom");
        modelAndView.setViewName("room");
        modelAndView.addObject("rooms",restRoomList);
        modelAndView.addObject("startDay",restRoomMap.get("startDay"));
        modelAndView.addObject("endDay",restRoomMap.get("endDay"));
        modelAndView.addObject("userId",userId);
        return modelAndView;
    }

}
