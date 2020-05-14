package com.wen.friend;

import com.wen.common.utils.IdWorker;
import com.wen.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import javax.persistence.Id;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan({"com.wen.common.model"})
@ComponentScans({
        @ComponentScan("com.wen.common.handler"),
        @ComponentScan("com.wen.common.aspect")
})
public class FriendMain9010 {

    public static void main(String[] args) {
        SpringApplication.run(FriendMain9010.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

}
