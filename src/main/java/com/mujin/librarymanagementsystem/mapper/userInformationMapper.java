package com.mujin.librarymanagementsystem.mapper;

import com.mujin.librarymanagementsystem.pojo.userInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface userInformationMapper {
    //根据account获取对应的密码
    @Select("select * from user  where account = #{account}")
    userInformation getUserInformation(String account);


    @Select("select * from user")
    userInformation getAllUserInformation();


    @Select("UPDATE user SET loginStatus=#{loginStatus} WHERE account = #{account}")
    void updateUserStatus(@Param("account") String account, @Param("loginStatus") Integer loginStatus);
}
