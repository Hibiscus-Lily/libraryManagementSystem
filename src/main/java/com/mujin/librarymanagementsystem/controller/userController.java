package com.mujin.librarymanagementsystem.controller;


import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.constant.JwtConstant;
import com.mujin.librarymanagementsystem.common.constant.Key;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.loginStatusInformation;
import com.mujin.librarymanagementsystem.pojo.userInformation;
import com.mujin.librarymanagementsystem.service.loginStatusInformationService;
import com.mujin.librarymanagementsystem.service.userInformationService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mujin.librarymanagementsystem.util.RSAEncrypt.decrypt;


/*
  变量说明
  PRIVATE_KEY       密钥
  jurisdiction      判断用户权限
  jurisdiction == 0 管理员
  jurisdiction == 1 普通用户
  loginStatusInformation.getStatus() ==  0  用户未登录
  loginStatusInformation.getStatus() ==  1  用户登录

 */


/**
 * 用户相关
 */
@RestController
@RequestMapping(value = "/user")
public class userController {

    private userInformationService userInformationService;
    private loginStatusInformationService loginStatusInformationService;

    @Autowired
    public void setUserInformationService(com.mujin.librarymanagementsystem.service.userInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Autowired
    public void setLoginStatusInformationService(com.mujin.librarymanagementsystem.service.loginStatusInformationService loginStatusInformationService) {
        this.loginStatusInformationService = loginStatusInformationService;
    }

    /**
     * 私钥解密登录验证
     *
     * @param account     登录账号
     * @param passwordRSA 两次加密后的密码
     */
    @PostMapping("/userLogin")
    public Result userLogin(@RequestParam String account, @RequestParam String passwordRSA, HttpServletResponse response) throws Exception {
        Map<String, Object> userInformationMap = new HashMap<>();
        userInformation userInformation = userInformationService.getUserInformation(account);  //根据用户输入得到account获取相应的信息
        loginStatusInformation loginStatusInformation = loginStatusInformationService.getUserLoginStatus(account);  //根据用户输入得到account获取相应的信息

        String msg = null;
        //查询到用户
        if (userInformation != null) {
            //判断对应的密码是否正确
            String userPassword = userInformation.getPassword(); //数据库存储的MD5密码
            String decryptPassword = decrypt(passwordRSA, Key.PRIVATE_KEY);  //解密之后的MD5
            boolean returnResults = userPassword.equals(decryptPassword);   //密码比对
            String token = JwtUtils.createJWT(account, userInformation.getJurisdiction(), 60 * 60 * 1000);

            //密码正确
            if (returnResults) {
                response.setHeader("token", token);
                if (loginStatusInformation != null) {
                    if (Objects.equals(loginStatusInformation.getStatus(), "0")) {
                        loginStatusInformationService.updateLoginStatus(account, "1");
                    }
                } else {
                    loginStatusInformationService.addLoginStatus(account, "1");
                }
                //管理员
                if (userInformation.getJurisdiction().equals("0")) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 0);
                    msg = userInformation.getUsername() + "欢迎登录";
                }
                //普通用户
                if (userInformation.getJurisdiction().equals("1")) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 1);
                    msg = userInformation.getUsername() + "欢迎登录";
                }
            } else {
                msg = "密码错误";
            }

            return new Result(Code.OK, userInformationMap, msg);
            //未查询到用户
        } else {
            userInformationMap.put("account", account);
            userInformationMap.put("jurisdiction", -1);
            return new Result(Code.NO_USERS, userInformationMap, "未查询到相应用户");
        }

    }

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


    /**
     * 无权限
     */
    @GetMapping("/noPermission")
    public Result noPermission() {
        return new Result(Code.OK, null, "无权限");
    }


}
