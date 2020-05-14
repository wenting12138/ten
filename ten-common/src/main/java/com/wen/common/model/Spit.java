package com.wen.common.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Spit implements Serializable {

    @Id
    private String _id;

    private String content;

    private Date publishTime;

    private String userId;

    // 别名
    private String nickName;
    // 浏览数
    private Integer visits;
    // 点赞数
    private Integer thumbup;
    // 分享数
    private Integer share;
    // 回复数
    private Integer comment;

    private String state;

    private String parentId;

}
