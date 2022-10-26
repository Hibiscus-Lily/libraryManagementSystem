package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.dao.userInformationDao;
import com.mujin.librarymanagementsystem.pojo.userInformation;
import com.mujin.librarymanagementsystem.service.userInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public List<userInformation> getAllUserInformation() {
        return userInformationDao.getAllUserInformation();
    }

    @Override
    public void updateUserStatus(String account, Integer loginStatus) {
        userInformationDao.updateUserStatus(account, loginStatus);

    }

    @Override
    public void userRegistration(String username, String account, String password) {
        userInformationDao.userRegistration(username, account, password);
    }

    @Override
    public void deleteUser(String account) {
        userInformationDao.deleteUser(account);
    }
}
