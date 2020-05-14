package com.wen.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.utils.IdWorker;
import com.wen.common.utils.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan({"com.wen.common.model"})
@EntityScan({"com.wen.common.model"})
@ComponentScans({
        @ComponentScan("com.wen.common.handler"),
        @ComponentScan("com.wen.common.aspect")
})
@EnableDiscoveryClient
public class UserMain9008 {

    public static void main(String[] args) {
        SpringApplication.run(UserMain9008.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
