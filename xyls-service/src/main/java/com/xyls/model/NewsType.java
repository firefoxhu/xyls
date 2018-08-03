package com.xyls.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ls_news_type")
public class NewsType {

    /**
     * 类型id
     */
    @Id
    @Column(length = 32)
    private String newsTypeId;

    /**
     * 父类id
     * root为根目录 改为栏目
     */
    @Column(length = 32)
    private String newsTypeParentId;

    /**
     * 类型名称
     */
    @Column(length = 32)
    private String newsTypeName;


    /**
     * 类型摘要
     */
    @Column(length = 128)
    private String newsTypeTitle;

    /**
     * 类型缩略图
     */
    @Column(length = 64)
    private String newsTypeThumbnail;

    /**
     * 类型导航url
     */
    @Column(length = 64)
    private String  newsTypeNavigationUrl;


    /**
     * 类型描述
     */
    @Column(length = 64)
    private String newsTypeDescription;

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
