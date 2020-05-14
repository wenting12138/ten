package com.wen.recruit;

import com.wen.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.wen.common.model")
@ComponentScans({
		@ComponentScan("com.wen.common.handler"),
		@ComponentScan("com.wen.common.aspect")
})
public class RecruitMain9002 {

	public static void main(String[] args) {
		SpringApplication.run(RecruitMain9002.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
