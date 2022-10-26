package com.mujin.librarymanagementsystem.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证令牌状态
 */
public class TokenUtils {

    public static Boolean verifyTokenStatus(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            if (permissionParameters.equals(1)) {
                return true;
            } else {
                response.sendRedirect("/libraryManagementSystem/login/noPermission");
                return false;
            }
        } else if (JWTcode == 4001) {
            response.sendRedirect("/libraryManagementSystem/login/tokenTimeout");
            return false;
        } else if (JWTcode == 4002) {
            response.sendRedirect("/libraryManagementSystem/login/tokenError");
            return false;
        } else {
            response.sendRedirect("/libraryManagementSystem/login/unknownError");
            return false;
        }
    }
}
