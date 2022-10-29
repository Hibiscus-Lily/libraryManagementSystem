package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>普通用户个人信息</h1>
 * <h3>是否鉴权  true</h3>
 * <p>鉴权文件 {@code OrdinaryUser_personalInformation_Interceptor }</p>
 */
@RestController
@RequestMapping("/commonuser/user")
public class GeneralUser_UserController {

    private UserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }


    /**
     * <h2>获取个人用户信息</h2>
     */
    @GetMapping("/information")
    public Result information(HttpServletRequest request) {
        String token = request.getHeader("token");
        String account = JwtUtils.validateJWT(token).getClaims().get("account").toString();
        UserInformationPojo userInformationPojo = userInformationService.getUserInformation(account);
        return new Result(Code.OK, userInformationPojo, "获取成功");

    }
}
