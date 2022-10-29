package com.mujin.librarymanagementsystem.controller.interceptor;

import com.mujin.librarymanagementsystem.util.JwtUtils;
import com.mujin.librarymanagementsystem.util.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class Admin_BookInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        int JWTcode = JwtUtils.validateJWT(token).getErrCode();
        String method = request.getMethod();
        if (!Objects.equals(method, "OPTIONS")) {
            if (token != null) {
                //4002-->   token错误
                //4001-->   token超时
                //0-->      正常
                TokenUtils.verifyPermissions(response, token, JWTcode);
            } else {
                response.sendRedirect("/libraryManagementSystem/user/notLoggedIn");
            }
        }

        return true;
    }

}
