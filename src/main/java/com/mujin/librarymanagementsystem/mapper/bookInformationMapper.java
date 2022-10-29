package com.mujin.librarymanagementsystem.mapper;


import com.mujin.librarymanagementsystem.pojo.bookInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface bookInformationMapper {
    @Select("select * from book")
    List<bookInformation> findBooks();

    @Select("select * from book  where title = #{title}")
    bookInformation findBooksByTitle(String title);

    @Select("DELETE from book where title= #{title}")
    void deleteBooks(String title);

    @Select("UPDATE book SET title=#{title}, author=#{author}, press=#{press}, year=#{year}, ISBN=#{isbn} WHERE title= #{title}")
    void updateBooks(@Param("title") String title, @Param("author") String author, @Param("press") String press, @Param("year") String year, @Param("isbn") String isbn);

    @Select("insert into book(title, author, press, year, isbn) values(#{title},#{author},#{press},#{year},#{isbn})")
    void addBooks(@Param("title") String title, @Param("author") String author, @Param("press") String press, @Param("year") String year, @Param("isbn") String isbn);


}

