package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.constant.Key;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserPojo;
import com.mujin.librarymanagementsystem.service.UserService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.mujin.librarymanagementsystem.util.RSAEncrypt.decrypt;

/**
 * <h1>登陆管理</h1>
 * <h3>是否鉴权  false</h3>
 * <h3>是否权限鉴权 false </h3>
 * <p>鉴权文件 {@code null }</p>
 */

@RestController
@RequestMapping("/login")
public class Login_Controller {

    private UserService userService;

    @Autowired
    public void setUserInformationService(UserService userService) {
        this.userService = userService;
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
        UserPojo userPojo = userService.getUserInformation(account);  //根据用户输入得到account获取相应的信息

        String msg = null;
        //查询到用户
        if (userPojo != null) {
            //判断对应的密码是否正确
            String userPassword = userPojo.getPassword(); //数据库存储的MD5密码
            String decryptPassword = decrypt(passwordRSA, Key.PRIVATE_KEY);  //解密之后的MD5
            boolean returnResults = userPassword.equals(decryptPassword);   //密码比对
            String token = JwtUtils.createJWT(account, userPojo.getJurisdiction(), 60 * 60 * 1000);

            //密码正确
            if (returnResults) {
                response.setHeader("token", token);
                if (userPojo.getLoginStatus() != null) {
                    if (userPojo.getState() == 0) {
                        userService.updateUserStatus(account, 1);
                    }
                } else {
                    userService.updateUserStatus(account, 0);
                }
                //管理员
                if (userPojo.getJurisdiction().equals(0)) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 0);
                    msg = userPojo.getUsername() + "欢迎登录";
                }
                //普通用户
                if (userPojo.getJurisdiction().equals(1)) {
                    userInformationMap.put("account", account);
                    userInformationMap.put("jurisdiction", 1);
                    msg = userPojo.getUsername() + "欢迎登录";
                }
            } else {
                userInformationMap.put("account", null);
                userInformationMap.put("jurisdiction", null);
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
     * 添加一个用户信息(注册)
     */
    @PostMapping
    public Result addAUserInformation(@RequestBody UserPojo userPojo) {
        UserPojo userInformation = userService.getUserInformation(userPojo.getAccount());
        if (userInformation == null) {
            Boolean result = userService.userRegistration(userPojo.getUsername(), userPojo.getAccount(), userPojo.getPassword());
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
     * 退出
     */
    @PostMapping("/SignOut")
    public Result SignOut(@RequestParam String account) {
        if (account != null) {
            userService.updateUserStatus(account, 0);
            return new Result(Code.OK, true, "退出成功");
        } else {
            return new Result(Code.OK, false, "退出登录失败");
        }

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

}
