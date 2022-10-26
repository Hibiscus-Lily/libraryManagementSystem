package com.mujin.librarymanagementsystem.controller;


import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.userInformation;
import com.mujin.librarymanagementsystem.service.userInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关
 */
@RestController
@RequestMapping(value = "/user")
public class userController {

    private userInformationService userInformationService;

    @Autowired
    public void setUserInformationService(com.mujin.librarymanagementsystem.service.userInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    /**
     * 获取所用用户信息并返回
     */

    @GetMapping("/getAllUserInformation")
    public Result getAllUserInformation() {
        List<userInformation> userInformation = userInformationService.getAllUserInformation();
        return new Result(Code.OK, userInformation, "获取数据成功");
    }


    /**
     * 获取一个用户信息
     */
    @GetMapping("/{account}")
    public Result getAUserInformation(@PathVariable String account) {
        userInformation userInformation = userInformationService.getUserInformation(account);  //根据account获取相应的信息
        return new Result(Code.OK, userInformation, "数据获取成功");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{account}")
    public Result deleteUser(@PathVariable String account) {
        userInformationService.deleteUser(account);  //根据account删除用户
        return new Result(Code.OK, true, account + "删除成功");
    }


}

