package com.example.hotel5.mapper;

import com.example.hotel5.entity.Orders;
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
 * @since 2020-12-23
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    @Select("SELECT * FROM orders WHERE roomNum = #{roomNum} order by startDay")
    List<Orders> findOrdersByRoomNum(@Param("roomNum")String roomNum);

    @Select("SELECT * FROM orders WHERE userId= #{userId} ORDER BY startDay")
    List<Orders> findOrdersByUserId(@Param("userId")int userId);

    @Delete("DELETE FROM orders WHERE userId=#{userId} AND roomType=#{roomType} AND roomNum=#{roomNum} AND startDay=#{startDay} AND endDay=#{endDay}")
    int deleteOrders(@Param("userId") int id,@Param("roomType")String roomType,@Param("roomNum")String roomNum,@Param("startDay")String startDay,@Param("endDay")String endDay);

    @Delete("DELETE FROM orders WHERE endDay=#{today}")
    int deleteAuto(@Param("today")String today);

    @Select("SELECT * FROM orders ORDER BY startDay")
    List<Orders> allOrder();

    @Select("SELECT * FROM orders WHERE roomType = #{roomType} order by startDay")
    List<Orders> findOrdersByRoomType(@Param("roomType") String roomType);
}
