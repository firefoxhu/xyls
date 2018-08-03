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
@Table(name = "ls_video")
public class Video {

    @Column(length = 32)
    @Id
    private String id;

    /**
     * 类型
     */
    @Column(length = 32)
    private String categoryId;

    @Column(length = 128)
    private String title;

    @Column(length = 256)
    private String abstracts;

    @Column(length = 128)
    private String url;

    @Column(length = 128)
    private String pic;

    @Column(length = 8)
    private String author;

    @Column(length = 8)
    private String source;

    @Column(length = 8)
    private String  playNumber;

    @Column(length = 8)
    private String fabulous;

    @Column(length = 1)
    private String status;

    @Column(length = 32)
    private String createPerson;

    @Column(length = 32)
    private String createTime;

    @Column(length = 32)
    private String createDescription;

    @Column(length = 32)
    private String modifyPerson;

    @Column(length = 32)
    private String modifyTime;

    @Column(length = 32)
    private String modifyDescription;



}
