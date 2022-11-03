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


    //根据id图书记录
    @Select("select * from borrowing  where id = #{id}")
    BorrowInformationPojo findBorrowingRecordsById(Integer id);

    //查询某本图书记录
    @Select("select * from borrowing  where title = #{title}")
    List<BorrowInformationPojo> findAllBorrowingRecordsForBookTile(String title);

    //查询某个用户图书记录
    @Select("select * from borrowing  where account = #{account}")
    List<BorrowInformationPojo> findAllBorrowingRecordsForUserAccount(String account);

    //根据Id号删除某条记录
    @Select("DELETE from borrowing where id= #{id}")
    void deletBorrowingRecords(Integer id);

    //根据ID更新某条记录
    @Select("UPDATE borrowing SET title=#{title}, account=#{account}, borrowingTime=#{borrowingTime}, bookReturnTime=#{bookReturnTime}, estimatedReturnTime=#{estimatedReturnTime} WHERE id= #{id}")
    void updateBorrowingRecords(@Param("id") Integer id, @Param("title") String title, @Param("account") String account, @Param("borrowingTime") Integer borrowingTime, @Param("bookReturnTime") Integer bookReturnTime, @Param("estimatedReturnTime") Integer estimatedReturnTime);

    //根据ID更新还书时间
    @Select("UPDATE borrowing SET bookReturnTime=#{bookReturnTime} WHERE id= #{id}")
    void updateBookReturnTimeById(@Param("id") Integer id, @Param("bookReturnTime") Integer bookReturnTime);


    //添加借阅记录

    @Select("insert into borrowing(title, account, borrowingTime, bookReturnTime, estimatedReturnTime) values(#{title},#{account},#{borrowingTime},#{bookReturnTime},#{estimatedReturnTime})")
    void addBorrowingRecords(@Param("title") String title, @Param("account") String account, @Param("borrowingTime") Integer borrowingTime, @Param("bookReturnTime") Integer bookReturnTime, @Param("estimatedReturnTime") Integer estimatedReturnTime);

    //更新还书时间
    @Select("UPDATE borrowing SET bookReturnTime=#{bookReturnTime} WHERE title=#{title} ORDER BY borrowingTime DESC LIMIT 1")
    void updateBookReturnTime(@Param("title") String title, @Param("bookReturnTime") Integer bookReturnTime);

    //根据书名获取最新时间借阅图书的相关信息
    @Select("select * FROM borrowing WHERE title=#{title} ORDER BY borrowingTime DESC LIMIT 0,1")
    BorrowInformationPojo theLatestInformationOnBorrowedBooks(String title);

}
