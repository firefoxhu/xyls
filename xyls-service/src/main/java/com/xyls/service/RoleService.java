package com.xyls.service;

import com.xyls.dto.dto.RoleMenuDTO;
import com.xyls.dto.form.RoleForm;
import com.xyls.rbac.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface RoleService {


    /**
     * 获取所有的角色信息
     *
     * @param pageable
     * @return
     */
    Page<SysRole> query(Pageable pageable);

    /**
     * 新增角色
     *
     * @param roleForm
     * @param userId
     */
    void save(RoleForm roleForm, String userId) throws Exception;

    /**
     * 删除一条或者多条信息
     *
     * @param ids
     */
    void remove(String ids);


    /**
     * 修改信息
     */
    void modify(RoleForm roleForm, String userId) throws Exception;

    /**
     * 根据id查找
     */
    SysRole query(String id);

    /**
     * 查询当前角色对应的所有权限
     *
     * @param roleId
     * @return
     */
    RoleMenuDTO roleBindResource(String roleId) throws InvocationTargetException, IllegalAccessException;


    /**
     * 角色关联资源
     *
     * @param roleId
     * @param resourceIds
     */
    void bind(String roleId, String resourceIds);

    /**
     * 角色解绑资源
     *
     * @param roleId
     * @param resourceIds
     */
    void unbind(String roleId, String resourceIds);

    /**
     * 获取所有的角色信息
     *
     * @return
     */
    List<SysRole> findAll();

}
