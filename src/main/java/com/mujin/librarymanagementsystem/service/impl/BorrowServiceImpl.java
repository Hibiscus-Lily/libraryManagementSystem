package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.BorrowMapper;
import com.mujin.librarymanagementsystem.pojo.BorrowPojo;
import com.mujin.librarymanagementsystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    private BorrowMapper borrowMapper;

    @Autowired
    public void setBorrowInformationMapper(BorrowMapper borrowMapper) {
        this.borrowMapper = borrowMapper;
    }

    @Override
    public List<BorrowPojo> findAllBorrowingRecords() {
        return borrowMapper.findAllBorrowingRecords();
    }

    @Override
    public List<BorrowPojo> findAllBorrowingRecordsForBookTile(String title) {
        return borrowMapper.findAllBorrowingRecordsForBookTile(title);
    }

    @Override
    public List<BorrowPojo> findAllBorrowingRecordsForUserAccount(String account) {
        return borrowMapper.findAllBorrowingRecordsForUserAccount(account);
    }

    @Override
    public BorrowPojo theLatestInformationOnBorrowedBooks(String title) {
        return borrowMapper.theLatestInformationOnBorrowedBooks(title);
    }

    @Override
    public Boolean deletBorrowingRecords(Integer id) {
        if (id != null) {
            borrowMapper.deletBorrowingRecords(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BorrowPojo findBorrowingRecordsById(Integer id) {
        return borrowMapper.findBorrowingRecordsById(id);
    }

    @Override
    public Boolean updateBorrowingRecords(Integer id, String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime) {
        if (id != null && title != null && account != null && borrowingTime != null && bookReturnTime != null && estimatedReturnTime != null) {
            borrowMapper.updateBorrowingRecords(id, title, account, borrowingTime, bookReturnTime, estimatedReturnTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean addBorrowingRecords(String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime) {
        if (title != null && account != null && borrowingTime != null && bookReturnTime != null && estimatedReturnTime != null) {
            borrowMapper.addBorrowingRecords(title, account, borrowingTime, bookReturnTime, estimatedReturnTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBookReturnTime(String title, Integer bookReturnTime,Integer state) {
        if (title != null && bookReturnTime != null) {
            borrowMapper.updateBookReturnTime(title, bookReturnTime,state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBookReturnTimeById(Integer id, Integer bookReturnTime) {
        if (id != null && bookReturnTime != null) {
            borrowMapper.updateBookReturnTimeById(id, bookReturnTime);
            return true;
        } else {
            return false;
        }
    }
}
