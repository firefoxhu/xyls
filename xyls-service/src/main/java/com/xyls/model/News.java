package com.xyls.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ls_news",indexes = {@Index(name="index_title",columnList = "newsTitle",unique = false)})
public class News  implements  Serializable{

    /**
     *新闻id
     */
    @Id
    @Column(length = 32)
    private String newsId;
    /**
     *新闻栏目id
     */
    @Column(length = 32)
    private String newsClsId;
    /**
     *新闻类型id
     */
    @Column(length = 32)
    private String newsTypeId;

    /**
     * 赞数量
     */
    @Column(length = 8)
    private String fabulous;
    /**
     * 新闻标题
     */
    @Column(length = 32)
    private String newsTitle;
    /**
     * 新闻摘要
     */
    @Column(length = 64)
    private String newsSecondTitle;
    /**
     * 新闻作者
     */
    @Column(length = 32)
    private String newsAuthor;
    /**
     * 新闻来源
     */
    @Column(length = 32)
    private String newsSource;
    /**
     * 新闻阅读次数
     */
    @Column(length = 8)
    private String newsViews;
    /**
     * 新闻是否顶置
     1、顶置
        其他 非顶置
     *
     */
    @Column(length = 1)
    private String newsIsTop;
    /**
     * 新闻是否开启评论
     1、能
     其他不能
     */
    @Column(length = 1)
    private String newsIsComment;

    @Column(length = 512)
    private String newsHomeThumbnail;
    /**
     * 新闻显示类型
     * L：左图片
       R：右图爿
       T：下3图爿
       F：下四图爿
       M：下视频
     */
    @Column(length = 1)
    private String newsShowType;

    /**
     * 状态
     * 0 等待审核
     * 1 立即发布
     */
    @Column(length = 1)
    private String status;

    /**
     * 新闻主体内容
     */
    @Lob
    private String newsContent;

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