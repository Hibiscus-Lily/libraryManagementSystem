package com.mujin.librarymanagementsystem.controller.interceptor;

import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mujin.librarymanagementsystem.util.TokenUtils.verifyTokenStatus;

@Component

public class userInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("User ---->preHandle");
        String token = request.getHeader("token");
        int JWTcode = JwtUtils.validateJWT(token).getErrCode();
        if (token != null) {
            //4002-->   token错误
            //4001-->   token超时
            //0-->      正常
            verifyTokenStatus(response, token, JWTcode);
        } else {
            response.sendRedirect("/libraryManagementSystem/user/notLoggedIn");
        }
        return true;
    }
}
