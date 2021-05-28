package com.example.hotel5.config;


import com.example.hotel5.mapper.OrdersMapper;
import com.example.hotel5.service.RoomsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class AutoDisOrderConfig {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    RoomsocketService roomsocketService;

    //3.添加定时任务
    @Scheduled(cron = "0 0 12 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    //cron设置刷新时间(直接设置未解析)
    //[秒][分][小时][日][月][周][年]
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = date.format(fmt);
        System.out.println(ordersMapper.deleteAuto(today));
    }

    //3.添加定时任务
    @Scheduled(cron = "0 0/10 * * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    //cron设置刷新时间(直接设置未解析)
    //[秒][分][小时][日][月][周][年]
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configSocket() {
        System.out.println("开始定时任务");
        roomsocketService.updateAllRoom();
    }
}
