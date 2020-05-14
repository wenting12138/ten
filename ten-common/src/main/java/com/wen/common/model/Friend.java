package com.wen.common.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_friend")
@Data
public class Friend implements Serializable {

    @Id
    @Column(name = "userid")
    private String userId;
    @Id
    @Column(name = "friendid")
    private String friendId;
    @Column(name = "islike")
    private String isLike;

}
