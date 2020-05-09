package com.wen.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
/**
 * 评论
 */
@Data
public class Comment implements Serializable {

    @Id
    private String _id;
    // 分布式id
    private String cId;
    // 文章id
    private String articleId;
    // 评论内容
    private String content;
    // 评论人的id
    private String userId;
    // 评论的父id
    private String parentId;
    // 评论时间
    private Date publishTime;
    // 点赞数
    private Integer thumbup;

}
