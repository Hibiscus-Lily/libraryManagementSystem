package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.userInformation;

public interface userInformationService {

    userInformation getUserInformation(String account);
    void updateUserStatus(String account, Integer loginStatus);
}
