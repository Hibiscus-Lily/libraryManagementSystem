package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class Login_Controller {
    /**
     * 未登录
     */
    @GetMapping("/notLoggedIn")
    public Result notLoggedIn() {
        return new Result(Code.NOT_LOGGED_IN, null, "未登录");
    }

    /**
     * 登录错误
     */
    @GetMapping("/loginError")
    public Result loginError() {
        return new Result(Code.Exception_ERROR, null, "登录错误");
    }

}
