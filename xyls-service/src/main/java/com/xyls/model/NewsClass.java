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
@Table(name = "ls_news_class")
public class NewsClass {

    /**
     * 栏目id
     */
    @Id
    @Column(length = 32)
    private String newsClsId;

    /**
     * 栏目是否是主栏目
     * root 主栏目
     * 其他为子栏目
     */
    @Column(length = 32)
    private String newsClsParentId;

    /**
     * 栏目名称
     */
    @Column(length = 32)
    private String newsClsName;

    /**
     * 栏目摘要
     */
    @Column(length = 64)
    private String newsClsTitle;

    /**
     * 栏目缩略图
     */
    @Column(length = 64)
    private String newsClsThumbnail;

    /**
     * 栏目导航url
     */
    @Column(length = 64)
    private String newsClsNavigationUrl;

    /**
     * 栏目状态
     */
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
