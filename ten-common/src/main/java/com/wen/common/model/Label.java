package com.wen.common.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_label")
public class Label {

    @Id
    private String id;

    // 标签名
    private String labelname;

    // 状态
    private String state;

    // 使用数量
    private Long count;

    // 关注数
    private Long fans;

    // 是否推荐
    private String recommend;
}
