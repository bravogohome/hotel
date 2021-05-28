package com.example.hotel5.mapper;

import com.example.hotel5.entity.Roomperfect;
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
public interface RoomperfectMapper extends BaseMapper<Roomperfect> {
    @Select("SELECT * FROM roomperfect where roomNum= #{roomNum}")
    List<Roomperfect> findRoom(@Param("roomNum") String roomNum);

    @Select("SELECT * FROM roomperfect")
    List<Roomperfect> findAll();
}
