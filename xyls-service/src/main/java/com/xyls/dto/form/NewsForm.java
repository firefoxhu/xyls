package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class NewsForm {

    private String newsId;

    @NotEmpty(message = "栏目类型不能为空！")
    private String newsClsId;

    @NotEmpty(message = "资讯类型不能为空！")
    private String newsTypeId;

    @NotEmpty(message = "标题不能为空！")
    private String newsTitle;

    private String newsSecondTitle;

    @NotEmpty(message = "作者不能为空！")
    private String newsAuthor;

    @NotEmpty(message = "作者不能为空！")
    private String newsSource;

    @NotEmpty(message = "顶置状态不能为空！")
    private String newsIsTop;

    @NotEmpty(message = "缩略图不能为空！")
    private String newsHomeThumbnail;

    @NotEmpty(message = "展现方式不能为空！")
    private String newsShowType;

    @NotEmpty(message = "线上状态不能为空！")
    private String status;


    @NotEmpty(message = "发布日期不能为空！")
    private String createTime;

    @NotEmpty(message = "信息内容不能为空！")
    private String newsContent;
}
