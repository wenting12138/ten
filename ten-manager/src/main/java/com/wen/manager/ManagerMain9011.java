package com.wen.manager;

import com.wen.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@ComponentScans({
        @ComponentScan("com.wen.common.handler"),
        @ComponentScan("com.wen.common.aspect")
})
public class ManagerMain9011 {

    public static void main(String[] args) {
        SpringApplication.run(ManagerMain9011.class, args);
    }


    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

}
