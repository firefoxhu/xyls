package com.xyls.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RoleForm {

    private String roleId;

    @NotEmpty(message = "角色名称不能为空！")
    private String roleName;

    private String description;

}
