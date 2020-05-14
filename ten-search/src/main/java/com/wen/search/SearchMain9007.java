package com.wen.search;

import com.wen.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans({
        @ComponentScan("com.wen.common.handler"),
        @ComponentScan("com.wen.common.aspect")
})
public class SearchMain9007 {

    public static void main(String[] args) {
        SpringApplication.run(SearchMain9007.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
