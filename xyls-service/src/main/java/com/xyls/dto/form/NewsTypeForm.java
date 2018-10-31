package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class NewsTypeForm {


    private String newsTypeId;

    @NotEmpty(message = "父类类型不能为空（如果无则自动为root）！")
    private String newsTypeParentId;

    private String newsTypeTitle;

    @NotEmpty(message = "类型名称不能为空！")
    private String newsTypeName;

    private String newsTypeNavigationUrl;

    private String newsTypeThumbnail;

    private String newsTypeDescription;

    private String createTime;
}
