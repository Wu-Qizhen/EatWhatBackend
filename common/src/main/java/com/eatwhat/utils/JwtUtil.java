package com.eatwhat.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成 JWT
     * 使用 Hs256 算法，私匙使用固定秘钥
     *
     * @param secretKey 秘钥
     * @param ttlMillis 过期时间
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成 JWT 的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 设置
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token 解密
     *
     * @param secretKey 秘钥
     * @param token     加密后的 Token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 得到 DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置解析
                .parseClaimsJws(token).getBody();
        return claims;
    }

}