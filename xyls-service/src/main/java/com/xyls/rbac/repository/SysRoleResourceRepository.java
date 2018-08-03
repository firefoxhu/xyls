package com.xyls.rbac.repository;
import com.xyls.rbac.domain.SysRoleResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleResourceRepository  extends XylsRepository<SysRoleResource> {

    @Query(value = "select distinct srr from  SysRoleResource srr where srr.roleId in (:roleIds) ")
    List<SysRoleResource> findByRoleId(@Param("roleIds") String[] roleIds);

   void deleteByRoleIdAndResourceId(String roleId,String resourceId);

}
