package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.loginStatusInformation;

public interface loginStatusInformationService {
    void updateLoginStatus(String account, String status);

    void addLoginStatus(String account, String status);

    loginStatusInformation getUserLoginStatus(String account);

}
