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

    //根据Id号删除某条记录
    Boolean deletBorrowingRecords(Integer id);

    //根据ID更新某条记录
    Boolean updateBorrowingRecords(Integer id, String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime);

}
