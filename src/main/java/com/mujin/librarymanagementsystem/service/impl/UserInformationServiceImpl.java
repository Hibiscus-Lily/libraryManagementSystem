package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.UserInformationMapper;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInformationServiceImpl implements UserInformationService {
    public UserInformationMapper userInformationMapper;

    @Autowired
    public void setUserInformationMapper(UserInformationMapper userInformationMapper) {
        this.userInformationMapper = userInformationMapper;
    }

    @Override
    public UserInformationPojo getUserInformation(String account) {
        if (account != null) {
            return userInformationMapper.getUserInformation(account);
        } else {
            return null;
        }
    }

    @Override
    public List<UserInformationPojo> getAllUserInformation() {
        return userInformationMapper.getAllUserInformation();
    }

    @Override
    public Boolean updateUserStatus(String account, Integer loginStatus) {
        if (account != null && loginStatus != null) {
            userInformationMapper.updateUserStatus(account, loginStatus);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean userRegistration(String username, String account, String password) {
        if (username != null && account != null && password != null) {
            userInformationMapper.userRegistration(username, account, password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String account) {
        if (account != null) {
            userInformationMapper.deleteUser(account);
            return true;
        } else {
            return false;
        }
    }
}
