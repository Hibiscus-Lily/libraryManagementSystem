package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.ReturnBookPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnBookService {

    List<ReturnBookPojo> findReturnBook();

    Boolean deleteReturnBook(String title);

    Boolean addReturnBook (@Param("title") String title, @Param("account") String account, @Param("time") Integer time);
}
