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
@Table(name = "ls_comment")
public class Comment {

    @Id
    @Column(length = 32)
    private String id;

    @Column(length = 32)
    private String commentator;

    @Column(length = 32)
    private String newsId;

    @Column(length = 128)
    private String content;

    @Column(length = 16)
    private String ip;

    @Column(length = 32)
    private String createTime;




}
