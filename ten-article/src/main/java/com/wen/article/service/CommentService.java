package com.wen.article.service;

import com.wen.common.model.Comment;
import com.wen.common.result.ResultService;

import java.util.List;

public interface CommentService {

    /**
     *  查询所有数据
     * @return
     */
    ResultService<List<Comment>> getAll();
    /**
     *  根据id 查询
     * @param commentId
     * @return
     */
    ResultService<Comment> getCommentById(String commentId);
    /**
     *  新增评论
     * @param comment
     * @return
     */
    ResultService<Comment> save(Comment comment);

    /**
     *  修改评论
     * @param comment
     * @return
     */
    ResultService<Void> update(Comment comment);

    /**
     *  删除评论
     * @param commentId
     * @return
     */
    ResultService<Void> delete(String commentId);

    /**
     *  根据文章id 查询评论
     * @param articleId
     * @return
     */
    ResultService<List<Comment>> selectByArticleId(String articleId);

    /**
     *  点赞
     * @param commentId
     * @return
     */
    ResultService<Void> addThumbup(String commentId);
}
