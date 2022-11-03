package com.mujin.librarymanagementsystem.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
     */
    public static void doNotVerifyPermissions(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            if (!permissionParameters.equals(1) && !permissionParameters.equals(0)) {
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
     *                 =
     */
    public static void verifyPermissions(HttpServletResponse response, String token, int JWTcode) throws IOException {
        if (JWTcode == 0) {
            Object permissionParameters = JwtUtils.validateJWT(token).getClaims().get("jurisdiction");
            if (!permissionParameters.equals(1)) {
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
     * adminToken验证
     *
     * @param request  HttpServletResponse对象
     * @param response 传进token的值
     */
    public static boolean adminTokenVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        int JWTcode = JwtUtils.validateJWT(token).getErrCode();
        String method = request.getMethod();
        if (!Objects.equals(method, "OPTIONS")) {
            if (token != null) {
                //4002-->   token错误
                //4001-->   token超时
                //0-->      正常
                if (token.equals("null")) {
                    response.sendRedirect("/libraryManagementSystem/login/notLoggedIn");
                }
                verifyPermissions(response, token, JWTcode);
            } else {
                response.sendRedirect("/libraryManagementSystem/login/notLoggedIn");

            }
        }

        return true;
    }

    public static boolean OrdinaryUser_TokenVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        int JWTcode = JwtUtils.validateJWT(token).getErrCode();
        String method = request.getMethod();
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
