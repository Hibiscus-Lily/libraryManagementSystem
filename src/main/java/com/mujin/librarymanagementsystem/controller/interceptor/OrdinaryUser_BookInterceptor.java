package com.mujin.librarymanagementsystem.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mujin.librarymanagementsystem.util.TokenUtils.OrdinaryUser_TokenVerification;


/**
 * <h1>普通用户书籍信息鉴权</h1>
 * <h2>允许权限说明   --->    不进行权限鉴权--->doNotVerifyPermissions</h2>
 * <h3>鉴权调用访问书籍信息接口时是否登录或者一系列的token问题</h3>
 * <p>鉴权文件 {@code OrdinaryUser_BookController}</p>
 */
@Component
public class OrdinaryUser_BookInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return OrdinaryUser_TokenVerification(request, response);
    }


}
