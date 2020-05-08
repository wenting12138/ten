package com.wen.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.wen.common.model"})
public class ArticleMain9004 {

    public static void main(String[] args) {

        SpringApplication.run(ArticleMain9004.class, args);

    }

}
