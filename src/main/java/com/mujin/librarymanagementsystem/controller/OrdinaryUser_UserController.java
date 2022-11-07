package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.UserPojo;
import com.mujin.librarymanagementsystem.service.UserService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>普通用户个人信息</h1>
 * <h3>是否鉴权  true</h3>
 * <h3>是否权限鉴权 false </h3>
 * <p>鉴权文件 {@code OrdinaryUser_UserInterceptor }</p>
 */
@RestController
@RequestMapping("/commonuser/user")
public class OrdinaryUser_UserController {

    private UserService userService;

    @Autowired
    public void setUserInformationService(UserService userService) {
        this.userService = userService;
    }


    /**
     * <h2>获取个人用户信息</h2>
     */
    @GetMapping("/information")
    public Result information(HttpServletRequest request) {
        Map<String, Object> userPojoMap = new HashMap<>();
        String token = request.getHeader("token");
        String account = JwtUtils.validateJWT(token).getClaims().get("account").toString();
        UserPojo userPojo = userService.getUserInformation(account);
        userPojoMap.put("username", userPojo.getUsername());
        userPojoMap.put("account", userPojo.getAccount());
        userPojoMap.put("avatarurl", userPojo.getAvatarurl());
        userPojoMap.put("background", userPojo.getBackground());
        userPojoMap.put("jurisdiction", userPojo.getJurisdiction());
        return new Result(Code.OK, userPojoMap, "获取成功");
    }
}
