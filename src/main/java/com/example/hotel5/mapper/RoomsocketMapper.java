package com.example.hotel5.mapper;

import com.example.hotel5.entity.Room;
import com.example.hotel5.entity.Roomsocket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gohome
 * @since 2020-12-28
 */
@Repository
public interface RoomsocketMapper extends BaseMapper<Roomsocket> {
    @Select("SELECT * FROM roomsocket where roomNum= #{roomNum} ORDER BY id")
    List<Roomsocket> findNumber(@Param("roomNum") String roomNum);

    @Select("SELECT id FROM roomsocket ORDER BY id DESC LIMIT 1")
    List<Integer> findlast();

}
