package com.example.hotel5.mapper;

import com.example.hotel5.entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */

@Repository
public interface RoomMapper extends BaseMapper<Room> {

    @Select("SELECT * FROM room where roomNum= #{roomNum}")
    List<Room> findRoomByNum(@Param("roomNum") String roomNum);

    @Select("SELECT roomMoney FROM room where roomNum=#{roomNum}")
    List<Double> searchRoomMoney(@Param("roomNum")String roomNum);

    @Select("SELECT * FROM room")
    List<Room> findAllRoom();

    @Update("UPDATE room SET roomTemperature=#{temperature},roomWet=#{wet},roomPM=#{pm},roomNoise=#{noise} WHERE roomNum=#{roomNum}")
    int updateRoom(@Param("roomNum")String roomNum,@Param("temperature")String temperature,@Param("wet")String wet,@Param("pm")String pm,@Param("noise")String noise);
}
