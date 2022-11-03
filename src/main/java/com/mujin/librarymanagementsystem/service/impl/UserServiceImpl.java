package com.mujin.librarymanagementsystem.service.impl;

import com.mujin.librarymanagementsystem.mapper.UserMapper;
import com.mujin.librarymanagementsystem.pojo.UserPojo;
import com.mujin.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    public UserMapper userMapper;

    @Autowired
    public void setUserInformationMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserPojo getUserInformation(String account) {
        if (account != null) {
            return userMapper.getUserInformation(account);
        } else {
            return null;
        }
    }

    @Override
    public List<UserPojo> getAllUserInformation() {
        return userMapper.getAllUserInformation();
    }

    @Override
    public Boolean updateUserStatus(String account, Integer loginStatus) {
        if (account != null && loginStatus != null) {
            userMapper.updateUserStatus(account, loginStatus);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean userRegistration(String username, String account, String password) {
        if (username != null && account != null && password != null) {
            userMapper.userRegistration(username, account, password);
            return true;
        } else {
            return false;
        }
    }

    //管理员添加用户
    @Override
    public Boolean adminAddUser(String username, String account, String password, Integer state, Integer jurisdiction, Integer loginStatus) {
        if (username != null && account != null && password != null && state != null && jurisdiction != null && loginStatus != null) {
            userMapper.adminAddUser(username, account, password, state, jurisdiction, loginStatus);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String account) {
        if (account != null) {
            userMapper.deleteUser(account);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateUserInformation(String username, String account, String password, Integer state, Integer jurisdiction, Integer loginStatus) {
        if (username != null && account != null && password != null && state != null && jurisdiction != null && loginStatus != null) {
            userMapper.updateUserInformation(username, account, password, state, jurisdiction, loginStatus);
            return true;
        } else {
            return false;
        }
    }
}
