package com.wen.common;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTest {

    public static void main(String[] args) {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("张三")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 10 * 60 * 1000)) // 设置过期时间10分钟
                // HS256加密算法
                .signWith(SignatureAlgorithm.HS256, "123".getBytes())// 加盐
                .claim("role", "admin");  // 设置自定义属性


        System.out.println(jwtBuilder.compact());
    }

}
