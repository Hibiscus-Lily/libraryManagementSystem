package com.mujin.librarymanagementsystem.dao;

import com.mujin.librarymanagementsystem.pojo.loginStatusInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface loginStatusDao {

    //获取用户状态
    @Select("select * from loginStatus  where account = #{account}")
    loginStatusInformation getUserLoginStatus(String account);

    //更改用户状态
    @Select("UPDATE loginStatus SET account=#{account}, status=#{status} WHERE account=#{account}")
    void changeUserLoginStatus(@Param("account") String account, @Param("status") String status);

    //添加用户状态
    @Select("insert into loginStatus(account, status) values(#{account},#{status})")
    void addLoginStatus(@Param("account") String account, @Param("status") String status);
}
