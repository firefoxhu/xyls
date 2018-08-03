package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ResourceForm {

    private String resourceId;

    @NotEmpty(message = "资源名称不能为空！")
    private String resourceName;

    @NotEmpty(message = "资源父节点不能为空！")
    private String resourceParentId;

    @NotEmpty(message = "资源类型不能为空！")
    private String resourceType;

    @NotEmpty(message = "资源uri不能为空！")
    private String resourceUrl;

    private String resourceIcon = "icon-log";

    private String resourceSpread = "false";

}
