package com.wen.common.constant;

public interface UserConstant {

    Integer redisExepire = 5;  // 缓存过期时间

    String exchange = "ten_user"; // 发送验证码的交换机

    String routingKey = "sms_code";  //


    String authorization = "Authorization"; // 请求头

    String bearer = "Bearer ";

    String admin = "admin";
    String user = "user";
}
