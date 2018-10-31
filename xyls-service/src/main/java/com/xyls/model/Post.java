package com.xyls.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ls_post")
public class Post {

    @Id
    @Column(length = 32)
    private String pid;

    @Column(length = 256)
    private String content;

    /**
     * QQ
     * wx
     * phone任意一种
     */
    @Column(length = 32)
    private String concat;

    @Column(length = 128)
    private String pic;

    @Column(length = 32)
    private String ip;

    /**
     * 0 正常
     * 其它 顶置
     */
    @Column(length = 1)
    private String top;

    /**
     * 0 正常
     * 其它 过期
     */
    @Column(length = 1)
    private String status;

    @Column(length = 16)
    private String createDate;

    @Column(length = 32)
    private String createTime;

}
