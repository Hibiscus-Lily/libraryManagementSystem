package com.mujin.librarymanagementsystem.dao;

import com.mujin.librarymanagementsystem.pojo.userInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 用户
 */
@Mapper
public interface userInformationDao {
    //根据account获取对应的信息
    @Select("select * from user  where account = #{account}")
    userInformation getUserInformation(String account);

    //获取所有用户信息
    @Select("select * from user")
    List<userInformation> getAllUserInformation();

    //更新用户登录状态
    @Select("UPDATE user SET loginStatus=#{loginStatus} WHERE account = #{account}")
    void updateUserStatus(@Param("account") String account, @Param("loginStatus") Integer loginStatus);

    //用户注册（默认注册普通用户）
    @Select("insert into user(username,account, password) values(#{username},#{account},#{password})")
    void userRegistration(@Param("username") String username, @Param("account") String account, @Param("password") String password);

    //删除用户
    @Select("DELETE from user where account= #{account}")
    void deleteUser(@Param("account") String account);


}
