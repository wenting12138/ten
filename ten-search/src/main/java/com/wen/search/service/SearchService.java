package com.wen.search.service;

import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import com.wen.search.model.Article;
import com.wen.search.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IdWorker idWorker;


    public ResultService<Void> save(Article article) {
        articleRepository.save(article);
        article.setId(idWorker.nextId() + "");
        return new ResultService<>(true);
    }

    public ResultService<PageResult<Article>> searchPage(String key, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        Page<Article> articlePage = articleRepository.findByTitleOrContentLike(key, key, PageRequest.of(page - 1, size));
        if (articlePage == null) {
            return new ResultService<>(false);
        }
        List<Article> articles = articlePage.getContent();
        Long count = articlePage.getTotalElements();
        return new ResultService<>(true, articles.size(), new PageResult<>(count, articles));
    }
}
