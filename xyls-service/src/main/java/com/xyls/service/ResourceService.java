package com.xyls.service;

import com.xyls.dto.form.ResourceForm;
import com.xyls.rbac.domain.SysResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceService {

    /**
     获取所有的角色信息
     * @param pageable
     * @return
     */
    Page<SysResource> query(Pageable pageable);

    /**
     * 新增角色
     * @param resourceForm
     * @param userId
     */
    void save(ResourceForm resourceForm, String userId) throws Exception;

    /**
     * 删除一条或者多条信息
     * @param ids
     */
    void  remove(String ids);


    /**
     * 修改信息
     */
    void  modify(ResourceForm resourceForm, String userId) throws Exception;

    /**
     * 根据id查找
     */
    SysResource query(String id);

    /**
     * 初始化数据批量
     * @param sysResources
     */
    void  batch(List<SysResource> sysResources);

}
