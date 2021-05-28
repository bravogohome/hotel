package com.example.hotel5.mapper;

import com.example.hotel5.entity.Roomdouble;
import com.example.hotel5.entity.Roomstandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoomstandardMapper extends BaseMapper<Roomstandard> {
    @Select("SELECT * FROM roomstandard where roomNum= #{roomNum}")
    List<Roomstandard> findRoom(@Param("roomNum") String roomNum);

    @Select("SELECT * FROM roomstandard")
    List<Roomstandard> findAll();

}
