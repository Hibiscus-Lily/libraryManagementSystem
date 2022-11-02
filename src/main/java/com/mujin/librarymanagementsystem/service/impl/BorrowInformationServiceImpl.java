package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.BorrowInformationMapper;
import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;
import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowInformationServiceImpl implements BorrowInformationService {
    private BorrowInformationMapper borrowInformationMapper;

    @Autowired
    public void setBorrowInformationMapper(BorrowInformationMapper borrowInformationMapper) {
        this.borrowInformationMapper = borrowInformationMapper;
    }

    @Override
    public List<BorrowInformationPojo> findAllBorrowingRecords() {
        return borrowInformationMapper.findAllBorrowingRecords();
    }

    @Override
    public List<BorrowInformationPojo> findAllBorrowingRecordsForBookTile(String title) {
        return borrowInformationMapper.findAllBorrowingRecordsForBookTile(title);
    }

    @Override
    public List<BorrowInformationPojo> findAllBorrowingRecordsForUserAccount(String account) {
        return borrowInformationMapper.findAllBorrowingRecordsForUserAccount(account);
    }

    @Override
    public Boolean deletBorrowingRecords(Integer id) {
        if (id != null) {
            borrowInformationMapper.deletBorrowingRecords(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBorrowingRecords(Integer id, String title, String account, Integer borrowingTime, Integer bookReturnTime, Integer estimatedReturnTime) {
        if (id != null && title != null && account != null && borrowingTime != null && bookReturnTime != null && estimatedReturnTime != null) {
            borrowInformationMapper.updateBorrowingRecords(id, title, account, borrowingTime, bookReturnTime, estimatedReturnTime);
            return true;
        } else {
            return false;
        }
    }
}
