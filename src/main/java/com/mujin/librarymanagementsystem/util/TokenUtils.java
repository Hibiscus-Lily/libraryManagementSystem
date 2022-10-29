package com.mujin.librarymanagementsystem.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>Token校验工具</h1>
 * <p>校验传进Token的状态</p>
 * <br>
 * <p>4002-->   token错误 --->{@code /libraryManagementSystem/user/tokenTimeout}</p>
 * <p>4001-->   token超时 --->{@code /libraryManagementSystem/user/tokenError}</p>
 * <p>0-->      正常      --->{@code null}</p>
 * <br>
 * <p>doNotVerifyPermissions    --->    不进行权限校验</p>
 * <p>verifyPermissions         --->     进行权限校验</p>
 */
public class TokenUtils {
    /**
     * <h3>不进行权限校验</h3>
     *
     * @param response HttpServletResponse对象
     * @param token    传进token的值
     * @param JWTcode  token解析后的状态码
     * @throws IOException
     */
    public static void doNotVerifyPermissions(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            if (!permissionParameters.equals(1)&&!permissionParameters.equals(0)) {
                response.sendRedirect("/libraryManagementSystem/erroe/noPermission");
            }
        } else if (JWTcode == 4001) {
            response.sendRedirect("/libraryManagementSystem/erroe/tokenTimeout");
        } else if (JWTcode == 4002) {
            response.sendRedirect("/libraryManagementSystem/erroe/tokenError");
        } else {
            response.sendRedirect("/libraryManagementSystem/erroe/unknownError");
        }
    }

    /**
     * <h3>进行权限校验</h3>
     *
     * @param response HttpServletResponse对象
     * @param token    传进token的值
     * @param JWTcode  token解析后的状态码
     * @throws IOException
     */
    public static void verifyPermissions(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            if (!permissionParameters.equals(0)) {
                response.sendRedirect("/libraryManagementSystem/erroe/noPermission");
            }
        } else if (JWTcode == 4001) {
            response.sendRedirect("/libraryManagementSystem/erroe/tokenTimeout");
        } else if (JWTcode == 4002) {
            response.sendRedirect("/libraryManagementSystem/erroe/tokenError");
        } else {
            response.sendRedirect("/libraryManagementSystem/erroe/unknownError");
        }
    }

}
