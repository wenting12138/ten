package com.wen.article.config;

import com.wen.common.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {

    @Bean
    public IdWorker createIdwork(){
        return new IdWorker(1, 1);
    }

}
