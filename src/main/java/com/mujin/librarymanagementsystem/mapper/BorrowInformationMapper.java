package com.mujin.librarymanagementsystem.mapper;

import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowInformationMapper {
    //查询所有借阅记录
    @Select("select * from borrowing")
    List<BorrowInformationPojo> findAllBorrowingRecords();

    //查询某本图书记录
    @Select("select * from borrowing  where title = #{title}")
    List<BorrowInformationPojo> findAllBorrowingRecordsForBookTile(String title);

    //查询某个用户图书记录
    @Select("select * from borrowing  where account = #{account}")
    List<BorrowInformationPojo> findAllBorrowingRecordsForUserAccount(String account);
    //根据Id号删除某条记录
    @Select("DELETE from borrowing where id= #{id}")
    void deleteBooks(Integer title);

    //根据ID更新某条记录
    @Select("UPDATE book SET title=#{title}, author=#{author}, press=#{press}, year=#{year}, ISBN=#{isbn}, state=#{state} WHERE title= #{title}")
    void updateBooks(@Param("title") String title, @Param("author") String author, @Param("press") String press, @Param("year") String year, @Param("isbn") String isbn, @Param("state") Integer state);


}
