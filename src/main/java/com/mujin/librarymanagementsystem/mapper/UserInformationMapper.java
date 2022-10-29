package com.mujin.librarymanagementsystem.mapper;

import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserInformationMapper {
    //根据account获取对应的密码
    @Select("select * from user  where account = #{account}")
    UserInformationPojo getUserInformation(String account);


    @Select("select * from user")
    UserInformationPojo getAllUserInformation();


    @Select("UPDATE user SET loginStatus=#{loginStatus} WHERE account = #{account}")
    void updateUserStatus(@Param("account") String account, @Param("loginStatus") Integer loginStatus);
}
