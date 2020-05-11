package com.wen.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wen.article.constants.Contants;
import com.wen.article.dao.ArticleMapper;
import com.wen.article.dto.ArticleDto;
import com.wen.article.feignclient.NoticeClient;
import com.wen.article.service.ArticleService;
import com.wen.common.constant.ArticleConstant;
import com.wen.common.model.Article;
import com.wen.common.model.Notice;
import com.wen.common.result.PageResult;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private IdWorker idWorker;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NoticeClient noticeClient;

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
        String id = idWorker.nextId() + "";
        article.setId(id);
        // TODO user
        String userId = "123";
        article.setUserId(userId);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int effect = articleMapper.save(article);

        // 创建文章后, 发送消息给订阅者
        Set<String> members = redisTemplate.boundSetOps(authorSubscribeSetPrefix + userId).members();
        if (members == null || members.size() == 0) {
            return new ResultService<>(false);
        }
        Notice notice = null;
        for (String uid : members) {
            notice = new Notice();
            // 接收消息的用户id
            notice.setReceiverId(uid);
            // 进行操作的用户id
            notice.setOperatorId(userId);
            // 类型
            notice.setAction(Contants.publish);
            notice.setTargetType(Contants.article);
            // 文章id
            notice.setTargetId(id);
            // 通知类型
            notice.setType(Contants.type);
            noticeClient.save(notice);
            notice = null;
        }

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
    public ResultService<PageResult<Article>> selectByCondition(ArticleDto dto) {
        Article article = wrapperArticle(dto);
        article.setId(dto.getId());
        List<Article> articles = articleMapper.selectByCondition(article);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        Integer count = articles.size();
        return new ResultService<>(true, 1, new PageResult<>(count.longValue(), articles));
    }

    @Override
    @Transactional
    public ResultService<Void> delete(String articleId) {
        articleMapper.deleteById(articleId);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<PageResult<Article>> selectByConditionPageSize(ArticleDto dto, Integer page, Integer size) {
        Article article = wrapperArticle(dto);
        article.setId(dto.getId());
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.selectByConditionPageSize(article, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        articles = pageInfo.getList();
        Long count = pageInfo.getTotal();
        return new ResultService<>(true, 1, new PageResult<>(count, articles));
    }

    @Override
    public ResultService<Void> addThumbup(String articleId) {
        Article article = articleMapper.selectOneById(articleId);
        if (article == null) {
            return new ResultService<>(false);
        }
        articleMapper.addThumbup(articleId);
        // 点赞成功后 发送消息给作者
        Notice notice = new Notice();
        // 设置消息接收者的id: 文章作者id
        notice.setReceiverId(article.getUserId());
        // TODO userId
        String userId = "123";
        // 设置操作者的id
        notice.setOperatorId(userId);
        // 设置操作类型
        notice.setAction(Contants.publish);
        // 被操作的对象
        notice.setTargetType(Contants.article);
        // 对象id
        notice.setTargetId(articleId);
        // 通知类型
        notice.setType(Contants.user);
        noticeClient.save(notice);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<PageResult<Article>> getArticleByChannelPageSize(String channel, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.getArticleByChannelPageSize(channel, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        articles = pageInfo.getList();
        Long count = pageInfo.getTotal();
        return new ResultService<>(true, 1, new PageResult<>(count, articles));
    }

    @Override
    public ResultService<PageResult<Article>> getArticleBycolumnIdPageSize(String columnId, int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size > 100) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.getArticleBycolumnIdPageSize(columnId, page, size);
        if (articles == null || articles.isEmpty()) {
            return new ResultService<>(false);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        articles = pageInfo.getList();
        Long count = pageInfo.getTotal();
        return new ResultService<>(true, 1, new PageResult<>(count, articles));
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
    private static final String userSubscribeSetPrefix = "article_user_subscribe_";
    private static final String authorSubscribeSetPrefix = "article_author_subscribe_";

    @Override
    public ResultService<Void> subscribe(String articleId, String userId) {
        // 判断文章是否存在, 如果不存在, 就返回
        Article article = articleMapper.selectOneById(articleId);
        if (article == null) {
            return new ResultService<>(false);
        }
        // 获取文章作者id
        String articleUserId = article.getUserId();
        // 存放用户订阅信息的集合
        String userSubscribeSetKey = userSubscribeSetPrefix + userId;
        // 存放作者的订阅者的集合
        String authorSubscribeSetKey = authorSubscribeSetPrefix + articleUserId;
        // 先检查用户是否已经订阅
        Boolean isSubscribe = redisTemplate.boundSetOps(userSubscribeSetKey).isMember(articleUserId);
        if (isSubscribe) {
            // 把文章作者id用户的订阅作者set集合删除
            redisTemplate.boundSetOps(userSubscribeSetKey).remove(articleUserId);
            // 把用户id存在文章用户的订阅者set集合删除
            redisTemplate.boundSetOps(authorSubscribeSetKey).remove(userId);
            return new ResultService<>(false);
        }else {
            // 把文章作者id存入用户的订阅作者set集合中
            redisTemplate.boundSetOps(userSubscribeSetKey).add(articleUserId);
            // 把用户id存在文章用户的订阅者set集合中
            redisTemplate.boundSetOps(authorSubscribeSetKey).add(userId);
            return new ResultService<>(true);
        }
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
