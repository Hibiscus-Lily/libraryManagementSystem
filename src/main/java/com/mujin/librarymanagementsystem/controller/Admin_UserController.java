package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserPojo;
import com.mujin.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private UserService userService;

    @Autowired
    public void setUserInformationService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取所用用户信息并返回
     */
    @GetMapping("/getAllUserInformation")
    public Result getAllUserInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<UserPojo> userPojos = userService.getAllUserInformation();
        PageInfo<UserPojo> pageInfo = new PageInfo<>(userPojos);
        return new Result(Code.OK, pageInfo, "获取数据成功");
    }


    /**
     * 获取一个用户信息并返回
     */
    @GetMapping("/{account}")
    public Result getUserInformation(@RequestParam Integer page, @RequestParam Integer limit, @PathVariable String account) {
        PageHelper.startPage(page, limit);
        UserPojo userPojo = userService.getUserInformation(account);
        List<UserPojo> userPojos = new ArrayList<>();
        if (userPojo == null) {
            return new Result(Code.OK, null, "数据为空");
        } else {
            userPojos.add(userPojo);
            return new Result(Code.OK, userPojos, "数据获取成功");
        }
    }


    /**
     * 添加一个用户信息(不等于注册)
     */
    @PostMapping
    public Result addAUserInformation(@RequestBody UserPojo userPojo) {
        UserPojo userInformation = userService.getUserInformation(userPojo.getAccount());
        if (userInformation == null) {
            Boolean result = userService.adminAddUser(userPojo.getUsername(), userPojo.getAccount(), userPojo.getPassword(), userPojo.getState(), userPojo.getJurisdiction(), userPojo.getLoginStatus());
            if (result) {
                return new Result(Code.OK, true, userPojo.getAccount() + "&nbsp&nbsp&nbsp注册成功");
            } else {
                return new Result(Code.OK, false, "检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, "账号重复，请更换");
        }
    }


    /**
     * 更新用户信息
     */
    @PutMapping
    public Result updateUserInformation(@RequestBody UserPojo userPojo) {
        UserPojo userInformation = userService.getUserInformation(userPojo.getAccount());
        if (userInformation != null) {
            Boolean result = userService.updateUserInformation(userPojo.getUsername(), userPojo.getAccount(), userPojo.getPassword(), userPojo.getState(), userPojo.getJurisdiction(), userPojo.getLoginStatus());
            if (result) {
                return new Result(Code.OK, true, userPojo.getAccount() + "更新成功");
            } else {
                return new Result(Code.OK, false, "检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, "更新的用户不存在");

        }

    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{account}")
    public Result deleteUsers(@PathVariable String account) {
        UserPojo userPojo = userService.getUserInformation(account);
        if (userPojo != null) {
            Boolean result = userService.deleteUser(account);
            if (result) {
                return new Result(Code.OK, true, "删除成功");
            } else {
                return new Result(Code.OK, false, "检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, "删除的用户不存在");
        }
    }
}
