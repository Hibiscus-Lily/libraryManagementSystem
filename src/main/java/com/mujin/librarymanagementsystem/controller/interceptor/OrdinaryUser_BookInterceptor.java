package com.mujin.librarymanagementsystem.controller.interceptor;

import com.mujin.librarymanagementsystem.util.JwtUtils;
import com.mujin.librarymanagementsystem.util.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;



/**
 * <h1>普通用户书籍信息鉴权</h1>
 * <h2>允许权限说明   --->    不进行权限鉴权--->doNotVerifyPermissions</h2>
 * <h3>鉴权调用访问书籍信息接口时是否登录或者一系列的token问题</h3>
 * <p>鉴权文件 {@code OrdinaryUser_BookController}</p>
 */
@Component
public class OrdinaryUser_BookInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        int JWTcode = JwtUtils.validateJWT(token).getErrCode();
        String method = request.getMethod();
        System.out.println(method);
        if (!Objects.equals(method, "OPTIONS")) {
            if (token != null) {
                //4002-->   token错误
                //4001-->   token超时
                //0-->      正常
                if (token.equals("null")) {
                    response.sendRedirect("/libraryManagementSystem/login/notLoggedIn");
                }
                TokenUtils.doNotVerifyPermissions(response, token, JWTcode);
            } else {
                response.sendRedirect("/libraryManagementSystem/login/notLoggedIn");
            }
        }
        return true;
    }

}
