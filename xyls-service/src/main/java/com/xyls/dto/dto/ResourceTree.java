package com.xyls.dto.dto;

import lombok.Data;

@Data
public class ResourceTree {

    private boolean isHave = false;

    private String resourceId;

    private String resourceName;

    private String resourceParentId;

    private String resourceType;

    private String resourceUrl;

    private String resourceIcon = "icon-log";

    private String resourceSpread = "false";

    private String createTime;

    private String description;

}
