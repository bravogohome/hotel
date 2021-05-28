package com.example.hotel5.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.hotel5.entity.Room;
import com.example.hotel5.entity.Roomsocket;
import com.example.hotel5.mapper.RoomMapper;
import com.example.hotel5.mapper.RoomsocketMapper;
import com.example.hotel5.service.RoomsocketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gohome
 * @since 2020-12-28
 */
@Service
public class RoomsocketServiceImpl extends ServiceImpl<RoomsocketMapper, Roomsocket> implements RoomsocketService {

    @Autowired
    RoomsocketMapper roomsocketMapper;
    //@Autowired
    //private WebSocketClient webSocketClient;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    RoomsocketService roomsocketService;

    @Transactional
    public void updateAllRoom() {
        List<Room> allRooms=roomMapper.findAllRoom();
        for (int i=0;i<allRooms.size();i++){
           // webSocketClient.send(allRooms.get(i).getRoomNum());
            DecimalFormat df = new DecimalFormat( "0.00 ");
            String roomNum=allRooms.get(i).getRoomNum();
            String roomTemperature=String.valueOf(df.format((Math.random() *450% 500) / 10.0));
            String roomWet=String.valueOf(df.format((Math.random() *10% 100) / 20.0));
            String roomNoise=String.valueOf(df.format((Math.random() *500% 300) / 50.0));
            String roomPM=String.valueOf(df.format((Math.random() *700% 650) / 5.0));
            roomMapper.updateRoom(roomNum,roomTemperature,roomWet,roomPM,roomNoise);
            //addhistory(allRooms.get(i).getRoomNum());
            List<Roomsocket> roomsocketList=roomsocketMapper.findNumber(roomNum);
            if (roomsocketList.size()>=5){
                roomsocketMapper.deleteById(roomsocketList.get(0).getId());
            }
            Roomsocket roomsocket=new Roomsocket();
            int lastId=roomsocketMapper.findlast().get(0)+1;
            roomsocket.setId(lastId);
            roomsocket.setRoomNum(roomNum);
            roomsocket.setRoomTemperature(roomTemperature);
            roomsocket.setRoomWet(roomWet);
            roomsocket.setRoomPM(roomPM);
            roomsocket.setRoomNoise(roomNoise);
            save(roomsocket);
        }
    }


}
