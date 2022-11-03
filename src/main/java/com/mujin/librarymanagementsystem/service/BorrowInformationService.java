package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;

import java.util.List;

public interface BorrowInformationService {
    //查询所有借阅记录
    List<BorrowInformationPojo> findAllBorrowingRecords();

    //查询某本图书记录
    List<BorrowInformationPojo> findAllBorrowingRecordsForBookTile(String title);

    //查询某个用户图书记录
    List<BorrowInformationPojo> findAllBorrowingRecordsForUserAccount(String account);

    //根据书名获取最新时间借阅图书的相关信息
    BorrowInformationPojo theLatestInformationOnBorrowedBooks(String title);

    //根据Id号删除某条记录
    Boolean deletBorrowingRecords(Integer id);

    //根据Id号查询某条记录
    BorrowInformationPojo findBorrowingRecordsById(Integer id);

    //根据ID更新某条记录
    Boolean updateBorrowingRecords(Integer id, String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime);

    Boolean addBorrowingRecords(String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime);

    //更新最新的一条还书时间
    Boolean updateBookReturnTime(String title, Integer bookReturnTime);


    //根据ID更新还书时间
    Boolean updateBookReturnTimeById(Integer id, Integer bookReturnTime);

}
