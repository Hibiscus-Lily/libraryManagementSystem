package com.mujin.librarymanagementsystem.mapper;


import com.mujin.librarymanagementsystem.pojo.BookPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface BookMapper {
    @Select("select * from book")
    List<BookPojo> findBooks();

    @Select("select * from book  where title = #{title}")
    BookPojo findBooksByTitle(String title);

    @Select("DELETE from book where title= #{title}")
    void deleteBooks(String title);

    @Select("UPDATE book SET title=#{title}, author=#{author}, press=#{press}, year=#{year}, ISBN=#{isbn}, state=#{state} WHERE title= #{title}")
    void updateBooks(@Param("title") String title, @Param("author") String author, @Param("press") String press, @Param("year") String year, @Param("isbn") String isbn, @Param("state") Integer state);

    @Select("insert into book(title, author, press, year, isbn,state) values(#{title},#{author},#{press},#{year},#{isbn},#{state})")
    void addBooks(@Param("title") String title, @Param("author") String author, @Param("press") String press, @Param("year") String year, @Param("isbn") String isbn, @Param("state") Integer state);


    //更新书籍状态
    @Select("UPDATE book SET title=#{title},  state=#{state} WHERE title= #{title}")
    void updateBookStatus(@Param("title") String title, @Param("state") Integer state);
}

