package com.example.hotel5.mapper;

import com.example.hotel5.entity.Adminbase;
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
public interface AdminbaseMapper extends BaseMapper<Adminbase> {

    @Select("SELECT * FROM adminbase WHERE adminId=#{adminId}")
    List<Adminbase> findAdmin(@Param("adminId")String adminId);
}
