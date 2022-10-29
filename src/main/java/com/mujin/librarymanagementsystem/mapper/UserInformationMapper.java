package com.mujin.librarymanagementsystem.mapper;

import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 用户
 */
@Mapper
public interface UserInformationMapper {
    //根据account获取对应的信息
    @Select("select * from user  where account = #{account}")
    UserInformationPojo getUserInformation(String account);

    //获取所有用户信息
    @Select("select * from user")
    List<UserInformationPojo> getAllUserInformation();

    //更新用户登录状态
    @Select("UPDATE user SET loginStatus=#{loginStatus} WHERE account = #{account}")
    void updateUserStatus(@Param("account") String account, @Param("loginStatus") Integer loginStatus);

    //用户注册（默认注册普通用户）
    @Select("insert into user(username,account, password) values(#{username},#{account},#{password})")
    void userRegistration(@Param("username") String username, @Param("account") String account, @Param("password") String password);

    //删除用户
    @Select("DELETE from user where account= #{account}")
    void deleteUser(@Param("account") String account);

    //更新用户信息
    @Select("UPDATE user SET username=#{username}, account=#{account}, password=#{password}, state=#{state}, jurisdiction=#{jurisdiction}, loginStatus=#{loginStatus} WHERE  account=#{account}")
    void updateUserInformation(@Param("username") String username, @Param("account") String account, @Param("password") String password, @Param("state") Integer state, @Param("jurisdiction") Integer jurisdiction, @Param("loginStatus") Integer loginStatus);


}