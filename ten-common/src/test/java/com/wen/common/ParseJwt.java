package com.wen.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwt {

    public static void main(String[] args) {

        Claims claims = Jwts.parser().setSigningKey("123".getBytes())
                .parseClaimsJws("jwt")
                .getBody();
        // id
        System.out.println(claims.getId());
        // 用户名
        System.out.println(claims.getSubject());
        // 登录时间
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(claims.getIssuedAt()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(claims.getExpiration()));
        System.out.println(claims.get("role"));
    }

}
