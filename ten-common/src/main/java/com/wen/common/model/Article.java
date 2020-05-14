package com.wen.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
 *  文章
 */
@Data
@ToString
public class Article {

    // id
    private String id;

    // 专栏id
    private String columnId;

    // 用户id
    private String userId;

    // 文章标题
    private String title;

    // 文章内容
    private String content;

    // 文章封面
    private String image;

    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;

    // 是否公开 0: 不公开
    private String isPublic;

    // 是否置顶 0: 不置顶
    private String isTop;

    // 浏览量
    private Integer visits;

    // 点赞量
    private Integer thumbup;

    // 评论量
    private Integer comment;

    // 审核状态  0: 未审核  1: 已审核
    private String state;

    // 所属频道
    private String channelId;

    // url地址
    private String url;

    // 文章类型
    private String type;

}
