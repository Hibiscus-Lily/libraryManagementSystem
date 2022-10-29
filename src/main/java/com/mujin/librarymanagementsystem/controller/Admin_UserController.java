package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>管理员管理用户</h1>
 * <h3>是否鉴权  true</h3>
 * <h3>是否权限鉴权 true </h3>
 * <p>鉴权文件 {@code Admin_UserInterceptor.java }</p>
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
    public Result getAllUserInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<UserInformationPojo> userInformationPojos = userInformationService.getAllUserInformation();
        return new Result(Code.OK, userInformationPojos, "获取数据成功");
    }

    /**
     * 添加一个用户信息(注册)
     */
    @PostMapping
    public Result addAUserInformation(@RequestBody UserInformationPojo userInformationPojo) {
        Boolean a = userInformationService.userRegistration(userInformationPojo.getUsername(), userInformationPojo.getAccount(), userInformationPojo.getPassword());
        return new Result(Code.OK, a, userInformationPojo.getAccount() + "&nbsp&nbsp&nbsp注册成功");
    }
}
