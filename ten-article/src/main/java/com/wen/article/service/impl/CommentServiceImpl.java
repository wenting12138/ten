package com.wen.article.service.impl;

import com.wen.article.repository.CommentRepository;
import com.wen.article.service.CommentService;
import com.wen.common.model.Comment;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResultService<List<Comment>> getAll() {
        List<Comment> commentList = commentRepository.findAll();
        if (commentList == null || commentList.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, commentList.size(), commentList);
    }

    @Override
    public ResultService<Comment> getCommentById(String commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        if (!optional.isPresent()) {
            return new ResultService<>(false);
        }
        if (optional.get() != null) {
            return new ResultService<>(true, 1, optional.get());
        }
        return new ResultService<>(false);
    }

    @Override
    public ResultService<Comment> save(Comment comment) {
        // 生成id
        String id = idWorker.nextId() + "";
        comment.setCId(id);
        comment.setThumbup(0);
        // TODO 用户
        comment.setUserId(null);
        // 评论时间
        comment.setPublishTime(new Date());
        // 保存
        commentRepository.save(comment);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<Void> update(Comment comment) {
        // 新增评论
        commentRepository.save(comment);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<Void> delete(String commentId) {
        // 根据id删除
        commentRepository.deleteById(commentId);
        return new ResultService<>(true);
    }

    @Override
    public ResultService<List<Comment>> selectByArticleId(String articleId) {

        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        if (comments == null || comments.isEmpty()) {
            return new ResultService<>(false);
        }
        return new ResultService<>(true, comments.size(), comments);
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    public static final String THUMBUPFIX = "thumbup";

    @Override
    public ResultService<Void> addThumbup(String commentId) {
//        // 根据id查询出当前评论
//        Optional<Comment> optional = commentRepository.findById(commentId);
//        if (!optional.isPresent()) {
//            return new ResultService<>(false);
//        }
//        Comment comment = optional.get();
//        // 评论 + 1
//        comment.setThumbup(comment.getThumbup() + 1);
//        // 保存
//        commentRepository.save(comment);


        // 封装修改的查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        // 封装修改的值
        Update update = new Update();
        // TODO userId 用户模块
        String userId = "123";

        String key = THUMBUPFIX + "_" + commentId + "_" + userId;
        Object o = redisTemplate.boundValueOps(key);
        // 说明是点赞
        if (o == null) {
            // redis 没有这个用户的信息, 说明是要进行点赞 的
            update.inc("thumbup", 1);
            redisTemplate.boundValueOps(key).set("1");
        }else {
            // redis 里有这个用户的信息说明 是要取消点赞的
            update.inc("thumbup",   -1);
            redisTemplate.delete(key);
        }
        mongoTemplate.updateFirst(query, update, "comment");
        return new ResultService<>(true);
    }

}
