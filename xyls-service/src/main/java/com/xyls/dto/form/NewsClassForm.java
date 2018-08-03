package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class NewsClassForm {


    private String newsClsId;

    @NotEmpty(message = "栏目是否是是主栏目")
    private String newsClsParentId;

    @NotEmpty(message = "栏目名称！")
    private String newsClsName;

    @NotEmpty(message = "栏目标题！")
    private String newsClsTitle;

    @NotEmpty(message = "栏目缩略图！")
    private String newsClsThumbnail;

    @NotEmpty(message = "栏目导向url！")
    private String newsClsNavigationUrl;


}
