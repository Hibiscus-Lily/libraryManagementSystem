package com.mujin.librarymanagementsystem.controller;


import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.constant.Key;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserInformationPojo;
import com.mujin.librarymanagementsystem.service.UserInformationService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/user")
public class UserController {

    private UserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
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
        UserInformationPojo userInformationPojo = userInformationService.getUserInformation(account);  //根据用户输入得到account获取相应的信息

        String msg = null;
        //查询到用户
        if (userInformationPojo != null) {
            //判断对应的密码是否正确
            String userPassword = userInformationPojo.getPassword(); //数据库存储的MD5密码
            String decryptPassword = decrypt(passwordRSA, Key.PRIVATE_KEY);  //解密之后的MD5
            boolean returnResults = userPassword.equals(decryptPassword);   //密码比对
            String token = JwtUtils.createJWT(account, userInformationPojo.getJurisdiction(), 60 * 60 * 1000);

            //密码正确
            if (returnResults) {
                response.setHeader("token", token);
                if (userInformationPojo.getLoginStatus() != null) {
                    if (userInformationPojo.getState() == 0) {
                        userInformationService.updateUserStatus(account, 1);
                    }
                } else {
                    userInformationService.updateUserStatus(account, 0);
                }
                //管理员
                if (userInformationPojo.getJurisdiction().equals(0)) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 0);
                    msg = userInformationPojo.getUsername() + "欢迎登录";
                }
                //普通用户
                if (userInformationPojo.getJurisdiction().equals(1)) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 1);
                    msg = userInformationPojo.getUsername() + "欢迎登录";
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
     * 获取所用用户信息并返回
     */

    @GetMapping("getAllUserInformation")
    public Result getAllUserInformation() {
        UserInformationPojo userInformationPojo = userInformationService.getAllUserInformation();
        return new Result(Code.OK, userInformationPojo, "获取数据成功");
    }

}

