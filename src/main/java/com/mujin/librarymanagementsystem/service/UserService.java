package com.mujin.librarymanagementsystem.service;

import com.mujin.librarymanagementsystem.pojo.UserPojo;

import java.util.List;

public interface UserService {

    /**
     * 获取一个用户信息
     *
     * @param account 用户账号
     * @return UserInformationPojo对象
     */
    UserPojo getUserInformation(String account);

    /**
     * 获取所有用户
     *
     * @return UserInformationPojo的list集合
     */
    List<UserPojo> getAllUserInformation();

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
     * 管理员添加用户
     *
     * @param username 用户名
     * @param account  账号
     * @param password 密码
     */
    Boolean adminAddUser(String username, String account, String password, Integer state, Integer jurisdiction, Integer loginStatus);


    /**
     * 删除用户
     */
    Boolean deleteUser(String account);


    Boolean updateUserInformation(String username, String account, String password, Integer state, Integer jurisdiction, Integer loginStatus);
}
