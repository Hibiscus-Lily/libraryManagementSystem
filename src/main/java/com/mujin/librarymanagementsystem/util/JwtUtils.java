package com.mujin.librarymanagementsystem.util;

import com.mujin.librarymanagementsystem.common.constant.JwtConstant;
import com.mujin.librarymanagementsystem.common.constant.Key;
import com.mujin.librarymanagementsystem.common.entity.CheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


/**
 * jwt加密和解密的工具类
 */
public class JwtUtils {

    /**
     * 签发JWT
     */
    public static String createJWT(String account, String jurisdiction, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId("1.0")
                .setSubject("libraryManagementSystem")   // 主题
                .setIssuer("木槿")     // 签发者
                .claim("account", account)
                .claim("jurisdiction", jurisdiction)
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     * 生成jwt token
     */
    public static String genJwtToken(String account, String jurisdiction) {
        return createJWT(account, jurisdiction, 60 * 60 * 1000);
    }

    /**
     * 验证JWT
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 生成加密Key
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Key.JWT_SECERT);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    /**
     * 解析JWT字符串
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
