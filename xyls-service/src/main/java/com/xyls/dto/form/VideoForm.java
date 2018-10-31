package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class VideoForm {

    private String id;

    @NotEmpty(message = "视频所属类别不能为空！")
    private String categoryId;

    @NotEmpty(message = "视频标题不能为空！")
    private String title;

    private String abstracts;

    @NotEmpty(message = "视频缩略图不能为空！")
    private String pic;

    @NotEmpty(message = "视频播放地址不能为空！")
    private String url;

    @NotEmpty(message = "作者不能为空！")
    private String author;

    @NotEmpty(message = "来源不能为空！")
    private String source;

    private String status;

    private String createTime;


}
