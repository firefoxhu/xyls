package com.xyls.dto.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RoleMenuDTO {

    private String roleId;

    private String roleName;

    private Map<String,List<ResourceTree>> map;

}
