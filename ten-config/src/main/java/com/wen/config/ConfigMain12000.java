package com.wen.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigMain12000 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigMain12000.class, args);
    }

}
