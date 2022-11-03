package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.ReturnBookMapper;
import com.mujin.librarymanagementsystem.pojo.ReturnBookPojo;
import com.mujin.librarymanagementsystem.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnBookServiceImpl implements ReturnBookService {

    public ReturnBookMapper returnBookMapper;

    @Autowired
    public void setReturnBookMapper(ReturnBookMapper returnBookMapper) {
        this.returnBookMapper = returnBookMapper;
    }


    @Override
    public List<ReturnBookPojo> findReturnBook() {
        return returnBookMapper.findReturnBook();
    }

    @Override
    public Boolean addReturnBook(String title, String account, Integer time) {

        if (title != null && account != null) {
            returnBookMapper.addReturnBook(title, account, time);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteReturnBook(String title) {
        if (title != null) {
            returnBookMapper.deleteReturnBook(title);
            return true;
        } else {
            return false;
        }
    }
}
