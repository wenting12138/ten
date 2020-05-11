package com.wen.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
// 开启feign远程调用
@EnableFeignClients
@MapperScan({"com.wen.common.model"})
@ComponentScans({
        // 异常捕获
        @ComponentScan("com.wen.common.handler"),
        // 日志aop
        @ComponentScan("com.wen.common.aspect")
})
public class NoticeMain9014 {

    public static void main(String[] args) {
        SpringApplication.run(NoticeMain9014.class, args);
    }

}
