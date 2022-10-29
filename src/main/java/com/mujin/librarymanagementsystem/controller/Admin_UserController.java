package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员用户相关
 */
@RestController
@RequestMapping("/admin/user")
public class Admin_UserController {
    private UserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    /**
     * 获取所用用户信息并返回
     */
    @GetMapping("/getAllUserInformation")
    public Result getAllUserInformation() {
        UserInformationPojo userInformationPojo = userInformationService.getAllUserInformation();
        return new Result(Code.OK, userInformationPojo, "获取数据成功");
    }
}
