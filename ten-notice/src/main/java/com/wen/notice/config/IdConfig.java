package com.wen.notice.config;

import com.wen.common.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {

    // id 生成器
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}
