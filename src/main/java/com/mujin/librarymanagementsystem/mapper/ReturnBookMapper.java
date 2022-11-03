package com.mujin.librarymanagementsystem.mapper;

import com.mujin.librarymanagementsystem.pojo.ReturnBookPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReturnBookMapper {
    @Select("select * from returnbook")
    List<ReturnBookPojo> findReturnBook();

    @Select("DELETE from returnbook where title= #{title}")
    void deleteReturnBook(String title);

    @Select("insert into returnbook(title, account,time) values(#{title},#{account},#{time})")
    void addReturnBook(@Param("title") String title, @Param("account") String account, @Param("time") Integer time);
}
