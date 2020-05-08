package com.wen.article.service.impl;

import com.wen.article.dao.ArticleMapper;
import com.wen.article.dto.ArticleDto;
import com.wen.article.service.ArticleService;
import com.wen.common.constant.ArticleConstant;
import com.wen.common.model.Article;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResultService<Article> selectOneById(String id) {
        Article article = articleMapper.selectOneById(id);
        if (article == null) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, 1, article);
    }

    @Override
    public ResultService<List<Article>> selectAll() {
        List<Article> articleList = articleMapper.selectAll();
        if (articleList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articleList.size(), articleList);
    }

    @Override
    @Transactional
    public ResultService<Void> save(ArticleDto articleDto) {
        // 将articleDto 转为 article
        Article article = wrapperArticle(articleDto);
        article.setId(new IdWorker().nextId() + "");
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int effect = articleMapper.save(article);
        if (effect == 0) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true);
    }

    @Override
    @Transactional
    public ResultService<Void> update(ArticleDto dto, String articleId) {
        Article article = wrapperArticle(dto);
        article.setUpdateTime(new Date());
        article.setIsPublic(articleId);
        int effect = articleMapper.update(article);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<List<Article>> selectByCondition(ArticleDto dto) {
        Article article = wrapperArticle(dto);
        article.setId(dto.getId());
        List<Article> articles = articleMapper.selectByCondition(article);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articles.size(), articles);
    }

    @Override
    @Transactional
    public ResultService<Void> delete(String articleId) {
        articleMapper.deleteById(articleId);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<List<Article>> selectByConditionPageSize(ArticleDto dto, Integer page, Integer size) {
        Article article = wrapperArticle(dto);
        article.setId(dto.getId());
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        page = (page - 1) * size;
        List<Article> articles = articleMapper.selectByConditionPageSize(article, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articles.size(), articles);
    }

    @Override
    public ResultService<Void> addThumbup(String articleId) {
        articleMapper.addThumbup(articleId);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<List<Article>> getArticleByChannelPageSize(String channel, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        page = (page - 1) * size;
        List<Article> articles = articleMapper.getArticleByChannelPageSize(channel, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articles.size(), articles);
    }

    @Override
    public ResultService<List<Article>> getArticleBycolumnIdPageSize(String columnId, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        page = (page - 1) * size;
        List<Article> articles = articleMapper.getArticleBycolumnIdPageSize(columnId, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articles.size(), articles);
    }

    @Override
    public ResultService<Void> check(String articleId) {
        articleMapper.check(articleId, ArticleConstant.state);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<List<Article>> topArticle() {
        List<Article> articles = articleMapper.topArticle(ArticleConstant.TOP);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, articles.size(), articles);
    }

    private Article wrapperArticle(ArticleDto articleDto) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDto, article);
        article.setChannelId(articleDto.getChannelid());
        article.setColumnId(articleDto.getColumnid());
        article.setIsPublic(articleDto.getIspublic());
        article.setIsTop(articleDto.getIstop());
        article.setUserId(articleDto.getUserid());
        return article;
    }
}
