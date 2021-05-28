package com.example.hotel5.mapper;

import com.example.hotel5.entity.Userbase;
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
public interface UserbaseMapper extends BaseMapper<Userbase> {

    @Select("SELECT * FROM userbase where phone = #{phone}")
    List<Userbase> selectByPhone(@Param("phone") String phone);

    @Select("SELECT * FROM userbase WHERE id<99900 ORDER BY id DESC LIMIT 1")
    List<Userbase> getLargestId();

    @Select("SELECT isAuthe FROM userbase WHERE id=#{userId}")
    List<Integer> userAutheStatus(@Param(value = "userId")int userId);

    @Select("SELECT * FROM userbase WHERE id=#{userId}")
    List<Userbase> findUserbaseById(@Param(value = "userId")int userId);

    @Select("SELECT * FROM userbase")
    List<Userbase> findAll();

    @Update("update userbase set phone=#{phone} where id=#{userId}")
    int updatePhone(@Param(value = "userId") int userId, @Param(value = "phone") String phone);
}
