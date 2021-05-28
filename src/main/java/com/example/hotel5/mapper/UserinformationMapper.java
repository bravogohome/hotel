package com.example.hotel5.mapper;

import com.example.hotel5.entity.Userinformation;
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
public interface UserinformationMapper extends BaseMapper<Userinformation> {

    @Update("UPDATE userinformation SET userWallet=#{newWallet} WHERE userId=#{userId}")
    int changeWallet(@Param("userId")int userId,@Param("newWallet")float newWallet);

    @Select("SELECT userWallet FROM userinformation WHERE userId=#{userId}")
    List<Float> findWalletById(@Param("userId") int userId);

    @Select("SELECT * FROM userinformation WHERE  userId=#{userId}")
    List<Userinformation> getUserInformationById(@Param("userId") int userId);

    @Update("UPDATE userinformation SET userName=#{userName},userIDNumber=#{userIDNumber},userSex=#{userSex} where userId=#{userId}")
    int updateAuthe(@Param("userId")int userId,@Param("userName")String userName,@Param("userSex")String userSex,@Param("userIDNumber")String userIDNumber);

    @Select("SELECT * FROM userinformation")
    List<Userinformation> findAll();

    @Update("Update userinformation SET isBlack=1 where userId=#{userId}")
    void addBlack(@Param("userId") int userId);
    @Update("Update userinformation SET isBlack=0 where userId=#{userId}")
    void removeBlack(@Param("userId") int userId);

    @Select("SELECT * FROM userinformation WHERE  userIDNumber=#{userIDNumber}")
    List<Userinformation> findUserByUserIDNumber(@Param("userIDNumber") String userIDNumber);
    @Select("SELECT * FROM userinformation WHERE  userName=#{userName}")
    List<Userinformation> findUserByUserName(@Param("userName") String userName);
    @Select("SELECT * FROM userinformation WHERE  userName=#{userName} and userIDNumber=#{userIDNumber}")
    List<Userinformation> findUserByUserNameAndUserIDNumber(@Param("userName") String userName,@Param("userIDNumber") String userIDNumber);
}
