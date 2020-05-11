package com.wen.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 *  用于保存用户的消息通知
 */
@Data
public class Notice {

    private String id;
    // 接收消息用户的id
    private String receiverId;
    // 进行操作用户的id
    private String operatorId;
    // 操作类型  评论    点赞
    private String action;
    // 被操作的对象, 文章  评论
    private String targetType;
    // 被操纵对象的id  文章的id  评论的id
    private String targetId;
    // 创建时间
    private Date createTime;
    private String type;
    // 状态  0: 未读  1: 已读
    private String state;

    @Transient   // 进行操作的用户昵称
    private String operatorName;

    @JsonIgnore
    @Transient
    private Long count; // 记录总数

    @Transient
    private String targetName;

}
