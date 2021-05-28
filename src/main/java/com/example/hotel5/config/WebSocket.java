package com.example.hotel5.config;


import com.example.hotel5.entity.Room;
import com.example.hotel5.entity.Roomsocket;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.service.RoomsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 这个注解有什么作用？
 * <p>
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 */

@Slf4j
@Component
@ServerEndpoint("/websocketservice/room")
public class WebSocket {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    @Autowired
    RoomMapper roomMapper;
    @Autowired
    RoomsocketService roomsocketService;
    /**
     * 标识当前连接客户端的用户名
     */
    private String roomNum;
    private Roomsocket roomsocket;
    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     */

    private static Map<String, WebSocket> webSocketSet = new HashMap<>();

    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "roomNum") String roomNum) {
        this.roomNum = roomNum;
        this.session = session;
        // roomNum是用来表示唯一客户端，如果需要指定发送，需要指定发送通过roomNum来区分
        roomsocket = new Roomsocket();
        webSocketSet.put(roomNum, this);
    }


    @OnClose
    public void OnClose() {
    }

    @OnMessage
    public void OnMessage(String roomNum) {
        //判断是否需要指定发送，具体规则自定义
        testRoom(roomNum);//模拟
        System.out.println("加");
        //roomMapper.updateRoom(roomsocket.getRoomNum(),roomsocket.getRoomTemperature(),roomsocket.getRoomWet(),roomsocket.getRoomPM(),roomsocket.getRoomNoise());
        //roomsocketService.addhistory(roomNum);
    }

    private void testRoom(String roomNum) {
        System.out.println("模拟"+roomNum);
        roomsocket.setRoomNum(roomNum);
        roomsocket.setRoomTemperature(String.valueOf((Math.random() % 500) / 10.0));
        roomsocket.setRoomWet(String.valueOf((Math.random() % 150) / 10.0));
        roomsocket.setRoomNoise(String.valueOf((Math.random() % 300) / 50.0));
        roomsocket.setRoomPM(String.valueOf((Math.random() % 650) / 5.0));
    }

}
