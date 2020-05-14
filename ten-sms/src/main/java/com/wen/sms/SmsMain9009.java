package com.wen.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SmsMain9009 {

    public static void main(String[] args) {
        SpringApplication.run(SmsMain9009.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
