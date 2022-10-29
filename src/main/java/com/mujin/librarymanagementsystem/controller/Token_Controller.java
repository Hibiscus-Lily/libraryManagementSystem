package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.constant.JwtConstant;
import com.mujin.librarymanagementsystem.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <h1>Token控制器</h1>
 * <h3>是否鉴权  false</h3>
 * <h3>是否权限鉴权 false </h3>
 * <p>Token控制器</p>
 * <p>重定向Token错误</p>
 * <p>鉴权文件 {@code null }</p>
 */


@RestController
@RequestMapping("/erroe")
public class Token_Controller {

    /**
     * token超时
     */
    @GetMapping("/tokenTimeout")
    public Result tokenTimeout() {
        return new Result(JwtConstant.JWT_ERRCODE_EXPIRE, null, "token超时，重新登录");
    }

    /**
     * token错误
     */

    @GetMapping("/tokenError")
    public Result tokenError() {
        return new Result(JwtConstant.JWT_ERRCODE_FAIL, null, "token错误，重新登录");
    }

    /**
     * 未知错误
     */
    @GetMapping("/unknownError")
    public Result unknownError() {
        return new Result(JwtConstant.JWT_ERRCODE_FAIL, null, "未知错误，重新登录");
    }


    /**
     * 无权限
     */
    @GetMapping("/noPermission")
    public Result noPermission() {
        return new Result(Code.OK, null, "无权限");
    }


}
