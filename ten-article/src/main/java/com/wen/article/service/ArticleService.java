package com.wen.article.service;

import com.wen.article.dto.ArticleDto;
import com.wen.common.model.Article;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;

import java.util.List;

public interface ArticleService {

    /**
     *  根据id查询
     * @param id
     * @return
     */
    ResultService<Article> selectOneById(String id);

    /**
     *  查询全部
     * @return
     */
    ResultService<List<Article>> selectAll();

    /**
     *  保存文章
     * @param articleDto
     * @return
     */
    ResultService<Void> save(ArticleDto articleDto);

    /**
     *  修改文章
     * @param article
     * @param articleId
     * @return
     */
    ResultService<Void> update(ArticleDto article, String articleId);

    /**
     *  条件查询
     * @param article
     * @return
     */
    ResultService<PageResult<Article>> selectByCondition(ArticleDto article);

    /**
     *  删除文章
     * @param articleId
     * @return
     */
    ResultService<Void> delete(String articleId);

    /**
     *  条件查询并且分页
     * @param article
     * @param page
     * @param size
     * @return
     */
    ResultService<PageResult<Article>> selectByConditionPageSize(ArticleDto article, Integer page, Integer size);

    /**
     *  点赞
     * @param articleId
     * @return
     */
    ResultService<Void> addThumbup(String articleId);

    /**
     *  根据频道id 分页查询文章
     * @param channel
     * @param page
     * @param size
     * @return
     */
    ResultService<PageResult<Article>> getArticleByChannelPageSize(String channel, int page, int size);

    /**
     *  根据专栏id分页查询文章列表
     * @param columnId
     * @param page
     * @param size
     * @return
     */
    ResultService<PageResult<Article>> getArticleBycolumnIdPageSize(String columnId, int page, int size);

    /**
     *  文章审核
     * @param articleId
     * @return
     */
    ResultService<Void> check(String articleId);

    /**
     *  文章置顶
     * @return
     */
    ResultService<List<Article>> topArticle();

    /**
     *  订阅作者
     * @param articleId
     * @param userId
     * @return
     */
    ResultService<Void> subscribe(String articleId, String userId);
}
