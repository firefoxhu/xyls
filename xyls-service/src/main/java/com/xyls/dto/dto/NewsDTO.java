package com.xyls.dto.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {

    private String newsId;

    private String newsClsId;

    private String newsTypeId;

    private String newsTitle;

    private String newsSecondTitle;

    private String newsAuthor;

    private String newsSource;

    private String newsViews;

    private String newsIsTop;

    private String newsIsComment;

    private String newsHomeThumbnail;

    private String newsShowType;

    private String createTime;

    private String newsContent;

    private String status;

    private String[] images;

    private String agoTime;

}
