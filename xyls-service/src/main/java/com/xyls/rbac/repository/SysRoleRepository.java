package com.xyls.rbac.repository;
import com.xyls.rbac.domain.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository  extends XylsRepository<SysRole> {

    @Query(value = "select sr from SysRole sr where sr.roleId in (:roleIds) ")
    List<SysRole> findByRoleId(@Param("roleIds") String[] roleIds);

    SysRole findByRoleName(String name);



}
