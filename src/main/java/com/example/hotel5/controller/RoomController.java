package com.example.hotel5.controller;



import com.example.hotel5.entity.Room;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.service.RoomService;
import com.example.hotel5.service.RoomdoubleService;
import com.example.hotel5.service.RoomperfectService;
import com.example.hotel5.service.RoomstandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomdoubleService roomdoubleService;
    @Autowired
    RoomperfectService roomperfectService;
    @Autowired
    RoomstandardService roomstandardService;
    @Autowired
    RoomService roomService;

    @GetMapping("/findRestRoom")
    public ModelAndView findRestRoom(@RequestParam(value = "roomType") String roomType, @RequestParam(value = "startDay") String startDay, @RequestParam(value = "endDay") String endDay,@RequestParam(value = "userId")String userId){
        ModelAndView modelAndView=new ModelAndView();
        switch(roomType){
            case "double":
                modelAndView.setViewName("redirect:/roomdouble/getRestRoom");
                modelAndView.addObject("startDay",startDay);
                modelAndView.addObject("endDay",endDay);
                break;
            case "standard":
                modelAndView.setViewName("redirect:/roomstandard/getRestRoom");
                modelAndView.addObject("startDay",startDay);
                modelAndView.addObject("endDay",endDay);
                break;
            case "perfect":
                modelAndView.setViewName("redirect:/roomperfect/getRestRoom");
                modelAndView.addObject("startDay",startDay);
                modelAndView.addObject("endDay",endDay);
                break;
            default:
        }
        modelAndView.addObject("userId",userId);
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/restRoomMessage")
    public Map<String,Object> getRestRoomMessage(@RequestParam(value = "roomType") String roomType, @RequestParam(value = "startDay") String startDay, @RequestParam(value = "endDay") String endDay){
        Map<String,Object> map=new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDay, fmt);
        LocalDate endDate=LocalDate.parse(endDay);
        switch(roomType){
            case "double":
                map=roomdoubleService.getRestRoom(startDate,endDate);
                break;
            case "standard":
                map=roomstandardService.getRestRoom(startDate,endDate);
                break;
            case "perfect":
                map=roomperfectService.getRestRoom(startDate,endDate);
                break;
            default:
        }
        return map;
    }

    @GetMapping("/roomDetail")
    public ModelAndView browseRoomDetail(@RequestParam(value = "roomNum") String roomNum){
        ModelAndView modelAndView=new ModelAndView();
        Room room=roomService.getRoom(roomNum);
        modelAndView.addObject("room",room);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @Autowired
    RoomMapper roomMapper;

    @GetMapping("/adminRoomPage")
    public ModelAndView adminRoomPage(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageRoom");
        List<Room> allRoom=roomMapper.findAllRoom();
        modelAndView.addObject("rooms",allRoom);
        return modelAndView;
    }
}
