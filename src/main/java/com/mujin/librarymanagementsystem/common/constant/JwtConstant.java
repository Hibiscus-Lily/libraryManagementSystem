package com.mujin.librarymanagementsystem.common.constant;

/**
 * JwtUtils态变量
 */
public class JwtConstant {

    /**
     * token
     */
    public static final int JWT_ERRCODE_EXPIRE = 4001;            //Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;            //验证不通过

    /**
     * JWT
     */
    public static final long JWT_TTL = 24 * 60 * 60 * 1000;                                    //token有效时间
}
