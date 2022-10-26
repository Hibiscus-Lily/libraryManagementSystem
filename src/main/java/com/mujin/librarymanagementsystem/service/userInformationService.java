package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.userInformation;

import java.util.List;

public interface userInformationService {

    userInformation getUserInformation(String account);

    List<userInformation> getAllUserInformation();

    void updateUserStatus(String account, Integer loginStatus);

    void userRegistration(String username, String account, String password);


    void deleteUser(String account);
}
