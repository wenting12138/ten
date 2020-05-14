package com.wen.spit;

import com.wen.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.wen.common.handler"),
        @ComponentScan("com.wen.common.aspect")
})
public class SpitMain9006 {

    public static void main(String[] args) {
        SpringApplication.run(SpitMain9006.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
