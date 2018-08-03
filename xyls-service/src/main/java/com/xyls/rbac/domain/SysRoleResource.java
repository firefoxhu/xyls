package com.xyls.rbac.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sys_role_resource_r")
public class SysRoleResource implements Serializable {


    @Id
    @Column(length = 32)
    private String roleResourceId;

    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String resourceId;

    @Column(length = 32)
    private String createTime;


}
