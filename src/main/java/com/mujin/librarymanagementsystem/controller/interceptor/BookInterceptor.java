package com.mujin.librarymanagementsystem.controller.interceptor;

import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.mujin.librarymanagementsystem.util.TokenUtils.verifyTokenStatus;


/**
 * Book过滤器
 */
@Component
public class BookInterceptor implements HandlerInterceptor {

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
                verifyTokenStatus(response, token, JWTcode);
            } else {
                response.sendRedirect("/libraryManagementSystem/user/notLoggedIn");
            }
        }

        return true;
    }

}
