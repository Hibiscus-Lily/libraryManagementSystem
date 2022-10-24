package com.mujin.librarymanagementsystem.dao;

import com.mujin.librarymanagementsystem.pojo.userInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface userInformationDao {
    //根据account获取对应的密码
    @Select("select password from user  where account = #{account}")
    userInformation getUserPassword(String account);

    @Select("select * from user  where account = #{account}")
    userInformation getUserInformation(String account);
}
