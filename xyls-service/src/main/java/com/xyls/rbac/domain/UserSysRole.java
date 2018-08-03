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
@Table(name = "sys_user_role_r")
public class UserSysRole implements Serializable {


    @Id
    @Column(length = 32)
    private String userRoleId;

    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String createTime;


}
