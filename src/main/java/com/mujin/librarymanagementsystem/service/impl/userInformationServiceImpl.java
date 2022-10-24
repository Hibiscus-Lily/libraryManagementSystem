package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.dao.userInformationDao;
import com.mujin.librarymanagementsystem.pojo.userInformation;
import com.mujin.librarymanagementsystem.service.userInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userInformationServiceImpl implements userInformationService {
    public userInformationDao userInformationDao;

    @Autowired
    public void setBookInformationDao(userInformationDao userInformationDao) {
        this.userInformationDao = userInformationDao;
    }


    @Override
    public userInformation getUserInformation(String account) {
        return userInformationDao.getUserInformation(account);
    }
}
