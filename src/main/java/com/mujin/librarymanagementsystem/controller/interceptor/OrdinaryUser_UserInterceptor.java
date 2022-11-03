package com.mujin.librarymanagementsystem.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mujin.librarymanagementsystem.util.TokenUtils.OrdinaryUser_TokenVerification;


/**
 * <h1>普通用户个人信息鉴权</h1>
 * <h2>鉴权调用访问用户个人信息接口时是否登录或者一系列的token问题</h2>
 * <h3>允许权限说明   ---> 不进行权限鉴权--->doNotVerifyPermissions</h3>
 * <p>鉴权文件 {@code OrdinaryUser_UserController}</p>
 */
@Component
public class OrdinaryUser_UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return OrdinaryUser_TokenVerification(request, response);
    }
}
