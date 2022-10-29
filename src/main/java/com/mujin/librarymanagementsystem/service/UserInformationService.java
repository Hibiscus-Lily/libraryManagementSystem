package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;

import java.util.List;

public interface UserInformationService {

    /**
     * 获取一个用户信息
     *
     * @param account 用户账号
     * @return UserInformationPojo对象
     */
    UserInformationPojo getUserInformation(String account);

    /**
     * 获取所有用户
     *
     * @return UserInformationPojo的list集合
     */
    List<UserInformationPojo> getAllUserInformation();

    /**
     * 更新用户在线状态
     *
     * @param account     用户账号
     * @param loginStatus 0-->离线,1--->在线
     */
    Boolean updateUserStatus(String account, Integer loginStatus);

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param account  账号
     * @param password 密码
     */
    Boolean userRegistration(String username, String account, String password);


    /**
     * 删除用户
     */
    Boolean deleteUser(String account);


    Boolean updateUserInformation(String usernam, String account, String password, Integer state, Integer jurisdiction, Integer loginStatus);
}
