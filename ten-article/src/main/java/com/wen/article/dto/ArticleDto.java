package com.wen.article.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDto {

    // id
    private String id;

    // 专栏id
    private String columnid;

    // 用户id
    private String userid;

    // 文章标题
    private String title;

    // 文章内容
    private String content;

    // 文章封面
    private String image;

    // 创建时间
    private Date createtime;
    // 修改时间
    private Date updatetime;

    // 是否公开 0: 不公开
    private String ispublic;

    // 是否置顶 0: 不置顶
    private String istop;

    // 浏览量
    private Integer visits = 0;

    // 点赞量
    private Integer thumbup = 0;

    // 评论量
    private Integer comment = 0;

    // 审核状态  0: 未审核  1: 已审核
    private String state;

    // 所属频道
    private String channelid;

    // url地址
    private String url;

    // 文章类型
    private String type;

}
