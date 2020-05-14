package com.wen.common.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_nofriend")
@Data
public class NoFriend implements Serializable {

    @Id
    @Column(name = "userid")
    private String userId;
    @Id
    @Column(name = "friendid")
    private String friendId;

}
