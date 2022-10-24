package com.mujin.librarymanagementsystem.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenUtils {
    /**
     * 验证令牌状态
     */
    public static void verifyTokenStatus(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            System.out.println(permissionParameters);
            if (!permissionParameters.equals("0")) {
                response.sendRedirect("/libraryManagementSystem/user/noPermission");
            }
        } else if (JWTcode == 4001) {
            response.sendRedirect("/libraryManagementSystem/user/tokenTimeout");
        } else if (JWTcode == 4002) {
            response.sendRedirect("/libraryManagementSystem/user/tokenError");
        } else {
            response.sendRedirect("/libraryManagementSystem/user/unknownError");
        }
    }
}
