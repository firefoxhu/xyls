package com.xyls.rbac.service;

import com.xyls.rbac.dto.SysRoleInfo;

import java.lang.reflect.InvocationTargetException;

public interface SysRoleService {

    /**
     * 创建角色
     *
     * @param sysRoleInfo
     * @return
     */
    SysRoleInfo create(SysRoleInfo sysRoleInfo);

    /**
     * 获取角色详细信息
     *
     * @param id
     * @return
     */
    SysRoleInfo getInfo(String id) throws InvocationTargetException, IllegalAccessException;

}
