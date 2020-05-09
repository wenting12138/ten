package com.wen.article.dao;

import com.wen.common.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper{

    Article selectOneById(@Param("id") String articleId);

    List<Article> selectAll();


    int save(Article article);

    int update(Article article);

    List<Article> selectByCondition(Article article);

    void deleteById(String articleId);

    List<Article> selectByConditionPageSize(@Param("article") Article article, @Param("page") Integer page,@Param("size") Integer size);

    void addThumbup(@Param("id") String articleId);

    List<Article> getArticleByChannelPageSize(@Param("channel") String channel,@Param("page") int page,@Param("size") int size);

    List<Article> getArticleBycolumnIdPageSize(@Param("columnId")String columnId,@Param("page") int page,@Param("size") int size);

    void check(@Param("id") String articleId, @Param("state") String state);

    List<Article> topArticle(String top);

    Long getCount();

}
