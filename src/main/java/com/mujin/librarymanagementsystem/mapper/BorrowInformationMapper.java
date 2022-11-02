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
    @Select("UPDATE book SET title=#{title}, account=#{account}, borrowingTime=#{borrowingTime}, bookReturnTime=#{bookReturnTime}, estimatedReturnTime=#{estimatedReturnTime}, state=#{state} WHERE id= #{id}")
    void updateBorrowingRecords(@Param("id") Integer id, @Param("title") String title, @Param("account") String account, @Param("borrowingTime") Integer borrowingTime, @Param("bookReturnTime") Integer bookReturnTime, @Param("estimatedReturnTime") Integer estimatedReturnTime);


}
