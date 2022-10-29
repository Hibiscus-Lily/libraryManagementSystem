package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;

public interface UserInformationService {

    UserInformationPojo getUserInformation(String account);
    UserInformationPojo getAllUserInformation();
    void updateUserStatus(String account, Integer loginStatus);
}
