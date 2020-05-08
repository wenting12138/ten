package com.wen.article.service.impl;

import com.wen.article.ArticleMain9004;
import com.wen.article.service.ArticleService;
import com.wen.common.model.Article;
import com.wen.common.result.ResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest(classes = ArticleMain9004.class)
@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {

    @Resource
    private ArticleService articleService;

    @Test
    public void selectOneById() {
        ResultService<Article> resultService = articleService.selectOneById("1");
        System.out.println(resultService.getRows());
    }
}