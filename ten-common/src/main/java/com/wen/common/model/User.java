package com.wen.common.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Entity
@Table(name="tb_user")
public class User {

    @Id
    private String id;  // id

    private String mobile; // 手机

    private String password;

    private String nickname; // 别名

    private String sex;  // 性别

    private Date birthday;  // 生日
    private String avatar;  // 头像

    private String email;  // 邮箱
    private Date regdate;  // 注册日期

    private Date updatedate; // 修改日期

    private Date lastdate;  // 最后登录的日期

    private Long online;  // 在线时长

    private String interest;   // 兴趣

    private String personality;  // 个性

    private Integer fanscount;  // 粉丝数

    private Integer followcount;  // 关注数

    @Transient
    private String role; // 角色
    @Transient
    private String jwtToken;  // 令牌



}
