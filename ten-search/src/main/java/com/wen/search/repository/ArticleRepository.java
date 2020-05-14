package com.wen.search.repository;

import com.wen.search.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {


    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);


}
