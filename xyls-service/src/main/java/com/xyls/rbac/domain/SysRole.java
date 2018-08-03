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
@Table(name = "sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID =  -1207488244908083516l;

    @Id
    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String roleName;

    @Column(length = 32)
    private String description;

    @Column(length = 32)
    private String createTime;
}
