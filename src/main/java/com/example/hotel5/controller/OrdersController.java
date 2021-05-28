package com.example.hotel5.controller;


import com.example.hotel5.entity.Orders;
import com.example.hotel5.mapper.OrdersMapper;
import com.example.hotel5.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @ResponseBody
    @GetMapping("/ordermessage")
    public Map<String,Object> getOrderMessage(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roomType") String roomType,
                                              @RequestParam(value = "roomNum") String roomNum, @RequestParam(value = "startDay")String startDay,
                                              @RequestParam(value = "endDay") String endDay){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDay, fmt);
        LocalDate endDate=LocalDate.parse(endDay);
        return ordersService.makeOrders(userId,roomType,roomNum,startDate,endDate);
    }

    @GetMapping("/takeOrder")
    public ModelAndView takeOrder( @RequestParam(value = "roomType") String roomType,
                                  @RequestParam(value = "roomNum") String roomNum, @RequestParam(value = "startDay")String startDay,
                                  @RequestParam(value = "endDay") String endDay,@RequestParam(value = "userId") Integer userId){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> takeOrderMessage=new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDay, fmt);
        LocalDate endDate=LocalDate.parse(endDay);
        takeOrderMessage=ordersService.makeOrders(userId,roomType,roomNum,startDate,endDate);
        switch((int)takeOrderMessage.get("errorCode")){
            case 0:
                modelAndView.addObject("makeordermessage",takeOrderMessage.get("message"));
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("redirect:/orders/findUserOrders");
                break;
            case 1:
                modelAndView.addObject("makeordermessage",takeOrderMessage.get("message"));
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("error");
                break;
            case 2:
                modelAndView.addObject("chargemessage",takeOrderMessage.get("message"));
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("redirect:/userinformation/getUserInformation");
                break;
            case 3:
                modelAndView.addObject("makeordermessage",takeOrderMessage.get("message"));
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("redirect:/userinformation/getUserInformation");
                break;
            case 4:
                modelAndView.addObject("makeordermessage",takeOrderMessage.get("message"));
                modelAndView.addObject("userId",userId);
                modelAndView.setViewName("redirect:/orders/findUserOrders");
                break;
            default:
        }
        return modelAndView;
    }

    @GetMapping("/findUserOrders")
    public ModelAndView findUserOrders(@RequestParam(value = "userId")String userId,String makeordermessage,String disordermessage){
        ModelAndView modelAndView=new ModelAndView();
        int id=Integer.valueOf(userId);
        Map<String, Object> ordersMessage=ordersService.getUserOrdersMessage(id);
        switch ((int)ordersMessage.get("errorCode")){
            case 0:
                modelAndView.setViewName("orders");
                List<Orders> ordersList=(List<Orders>) ordersMessage.get("ordersList");
                modelAndView.addObject("ordersList",ordersList);
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("disordermessage",disordermessage);
                modelAndView.addObject("makeordermessage",makeordermessage);
                modelAndView.addObject("message",ordersMessage.get("message"));
                break;
            case 1:
                modelAndView.setViewName("orders");
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("disordermessage",disordermessage);
                modelAndView.addObject("makeordermessage",makeordermessage);
                modelAndView.addObject("message",ordersMessage.get("message"));
                break;
            default:
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/getUserOrdersMessage")
    public Map<String,Object> getUserOrdersMessage(@RequestParam(value = "userId")String userId){
        int id=Integer.valueOf(userId);
        return ordersService.getUserOrdersMessage(id);
    }

    @GetMapping("/disOrder")
    public ModelAndView disOrder(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "roomType") String roomType,
                                 @RequestParam(value = "roomNum") String roomNum, @RequestParam(value = "startDay")String startDay,
                                 @RequestParam(value = "endDay") String endDay){
        ModelAndView modelAndView=new ModelAndView();
        Map<String,Object> disOrderMessage=getDisOrderMessage(userId,roomType,roomNum,startDay,endDay);
        switch ((int)disOrderMessage.get("errorCode")){
            case 0:
                modelAndView.addObject("userId",userId);
                modelAndView.addObject("disordermessage",disOrderMessage.get("message"));
                modelAndView.setViewName("redirect:/orders/findUserOrders");
                break;
            case 1:
                modelAndView.addObject("message",disOrderMessage.get("message"));
                modelAndView.setViewName("error");
                break;
            default:
        }
        return modelAndView;

    }

    @ResponseBody
    @GetMapping("/getDisOrderMessage")
    public Map<String,Object> getDisOrderMessage(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "roomType") String roomType,
                                                 @RequestParam(value = "roomNum") String roomNum, @RequestParam(value = "startDay")String startDay,
                                                 @RequestParam(value = "endDay") String endDay){
        return ordersService.disOrder(userId,roomType,roomNum,startDay,endDay);
    }

    @Autowired
    OrdersMapper ordersMapper;

    @GetMapping("/adminOrderPage")
    public ModelAndView adminOrderPage(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageOrder");
        List<Orders> allOrder=ordersMapper.allOrder();
        modelAndView.addObject("orders",allOrder);
        return modelAndView;
    }
    @GetMapping("/adminDisOrder")
    public ModelAndView adminDisOrder(@RequestParam(value = "userId")int userId,@RequestParam(value = "roomType")String roomType,
                                      @RequestParam(value = "roomNum")String roomNum,@RequestParam(value = "startDay")String startDay,
                                      @RequestParam(value = "endDay")String endDay){
        ModelAndView modelAndView=new ModelAndView();
        ordersMapper.deleteOrders(userId,roomType,roomNum,startDay,endDay);
        modelAndView.setViewName("redirect:/orders/adminOrderPage");
        return modelAndView;
    }

    @GetMapping("/query")
    public ModelAndView query(String userId,String roomNum){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageOrder");
        Map<String,Object> queryResult=new HashMap<>();
        if (userId.isEmpty()&&roomNum.isEmpty()){
            modelAndView.setViewName("redirect:/orders/adminOrderPage");
        }else if (userId.isEmpty()&&!roomNum.isEmpty()){
            List<Orders> ordersList=ordersMapper.findOrdersByRoomNum(roomNum);
            if (ordersList.isEmpty()){
                modelAndView.addObject("queryMessage","该用户没有订单");
            }else{
                modelAndView.addObject("orders",ordersList);
            }
        }else if (roomNum.isEmpty()&&!userId.isEmpty()){
            queryResult=ordersService.getUserOrdersMessage(Integer.valueOf(userId));
            switch ((int)queryResult.get("errorCode")){
                case 0:
                    modelAndView.addObject("orders",(List<Orders>)queryResult.get("ordersList"));
                    break;
                case 1:
                    modelAndView.addObject("queryMessage","该用户没有订单");
                    break;
                default:
            }
        }else{
            queryResult=ordersService.getUserOrdersMessage(Integer.valueOf(userId));
            switch ((int)queryResult.get("errorCode")){
                case 0:
                    List<Orders> ordersList=(List<Orders>)queryResult.get("ordersList");
                    List<Orders> orders=new ArrayList<>();
                    for (int i=0;i<ordersList.size();i++){
                        if(ordersList.get(i).getRoomNum().equals(roomNum)){
                            orders.add(ordersList.get(i));
                        }
                    }
                    if (orders.isEmpty()){
                        modelAndView.addObject("queryMessage","该用户没有订单");
                    }else{
                        modelAndView.addObject("orders",orders);
                    }
                    break;
                case 1:
                    modelAndView.addObject("queryMessage","该用户没有订单");
                    break;
                default:
            }
        }
        return modelAndView;
    }

    @GetMapping("/doubleOrders")
    public ModelAndView doubleOrders(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageOrder");
        List<Orders> doubleOrder=ordersMapper.findOrdersByRoomType("double");
        if (doubleOrder.isEmpty()){
            modelAndView.addObject("queryMessage","暂无该类订单");
        }else{
            modelAndView.addObject("orders",doubleOrder);
        }
        modelAndView.addObject("roomType","double");
        return modelAndView;
    }

    @GetMapping("/standardOrders")
    public ModelAndView standardOrders(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageOrder");
        List<Orders> doubleOrder=ordersMapper.findOrdersByRoomType("standard");
        if (doubleOrder.isEmpty()){
            modelAndView.addObject("queryMessage","暂无该类订单");
        }else{
            modelAndView.addObject("orders",doubleOrder);
        }
        modelAndView.addObject("roomType","standard");
        return modelAndView;
    }
    @GetMapping("/perfectOrders")
    public ModelAndView perfectOrders(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("manageOrder");
        List<Orders> doubleOrder=ordersMapper.findOrdersByRoomType("perfect");
        if (doubleOrder.isEmpty()){
            modelAndView.addObject("queryMessage","暂无该类订单");
        }else{
            modelAndView.addObject("orders",doubleOrder);
        }
        modelAndView.addObject("roomType","perfect");
        return modelAndView;
    }
}
