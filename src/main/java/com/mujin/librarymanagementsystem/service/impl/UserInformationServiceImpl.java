package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.UserInformationMapper;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInformationServiceImpl implements UserInformationService {
    public UserInformationMapper userInformationMapper;

    @Autowired
    public void setBookInformationDao(UserInformationMapper userInformationMapper) {
        this.userInformationMapper = userInformationMapper;
    }


    @Override
    public UserInformationPojo getUserInformation(String account) {
        return userInformationMapper.getUserInformation(account);
    }

    @Override
    public UserInformationPojo getAllUserInformation() {
        return userInformationMapper.getAllUserInformation();
    }

    @Override
    public void updateUserStatus(String account, Integer loginStatus) {
        userInformationMapper.updateUserStatus(account, loginStatus);

    }
}
