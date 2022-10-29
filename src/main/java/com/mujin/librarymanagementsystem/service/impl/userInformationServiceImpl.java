package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.userInformationMapper;
import com.mujin.librarymanagementsystem.pojo.userInformation;
import com.mujin.librarymanagementsystem.service.userInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userInformationServiceImpl implements userInformationService {
    public userInformationMapper userInformationMapper;

    @Autowired
    public void setBookInformationDao(userInformationMapper userInformationMapper) {
        this.userInformationMapper = userInformationMapper;
    }


    @Override
    public userInformation getUserInformation(String account) {
        return userInformationMapper.getUserInformation(account);
    }

    @Override
    public userInformation getAllUserInformation() {
        return userInformationMapper.getAllUserInformation();
    }

    @Override
    public void updateUserStatus(String account, Integer loginStatus) {
        userInformationMapper.updateUserStatus(account, loginStatus);

    }
}
