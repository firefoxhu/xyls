package com.xyls.rbac.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 系统资源表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sys_resource")
@ToString
public class SysResource implements Serializable{

    @Id
    @Column(length = 32)
    private String resourceId;

    @Column(length = 16)
    private String resourceName;

    @Column(length = 32)
    private String resourceParentId;

    @Column(length = 16)
    private String resourceType;

    @Column(length = 32)
    private String resourceUrl;

    @Column(length = 16)
    private String resourceIcon = "icon-log";

    @Column(length = 8)
    private String resourceSpread = "false";


    @Column(length = 32)
    private String createTime;

    @Column(length = 32)
    private String description;
}
