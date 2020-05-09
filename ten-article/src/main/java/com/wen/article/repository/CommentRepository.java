package com.wen.article.repository;

import com.wen.common.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * 根据文章id查询
     *
     * @param articleId
     * @return
     */
    List<Comment> findAllByArticleId(String articleId);

    /**
     *  根据发布时间和点赞数查询
     */
    List<Comment> findAllByPublishTimeAndThumbup(Date publishTime, Integer thumbup);

    /**
     *  根据用户id查询
     */
    List<Comment> findAllByUserIdOrderByPublishTimeDesc(String userId);
}
