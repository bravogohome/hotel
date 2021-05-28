package com.example.hotel5.mapper;

import com.example.hotel5.entity.Roomdouble;
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
public interface RoomdoubleMapper extends BaseMapper<Roomdouble> {
    @Select("SELECT * FROM roomdouble where roomNum= #{roomNum}")
    List<Roomdouble> findRoom(@Param("roomNum") String roomNum);

    @Select("SELECT * FROM roomdouble")
    List<Roomdouble> findAll();

}
