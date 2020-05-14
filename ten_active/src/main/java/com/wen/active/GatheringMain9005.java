package com.wen.active;

import com.wen.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableDiscoveryClient
@SpringBootApplication
// 开启本地缓存
@EnableCaching
@EntityScan("com.wen.common.model")
@ComponentScans({
		@ComponentScan("com.wen.common.handler"),
		@ComponentScan("com.wen.common.aspect")
})
public class GatheringMain9005 {

	public static void main(String[] args) {
		SpringApplication.run(GatheringMain9005.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
