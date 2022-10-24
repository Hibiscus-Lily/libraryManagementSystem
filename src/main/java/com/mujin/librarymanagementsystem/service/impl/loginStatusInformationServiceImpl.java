package com.mujin.librarymanagementsystem.service.impl;


import com.mujin.librarymanagementsystem.dao.loginStatusDao;
import com.mujin.librarymanagementsystem.pojo.loginStatusInformation;
import com.mujin.librarymanagementsystem.service.loginStatusInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginStatusInformationServiceImpl implements loginStatusInformationService {
    public loginStatusDao loginStatusDao;

    @Autowired

    public void setLoginStatusDao(com.mujin.librarymanagementsystem.dao.loginStatusDao loginStatusDao) {
        this.loginStatusDao = loginStatusDao;
    }

    @Override
    public void updateLoginStatus(String account, String status) {
        loginStatusDao.changeUserLoginStatus(account, status);
    }

    @Override
    public void addLoginStatus(String account, String status) {
        loginStatusDao.addLoginStatus(account, status);
    }

    @Override
    public loginStatusInformation getUserLoginStatus(String account) {
        return loginStatusDao.getUserLoginStatus(account);
    }
}
