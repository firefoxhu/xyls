package com.xyls.rbac.repository;

import com.xyls.rbac.domain.SysResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysResourceRepository extends XylsRepository<SysResource> {

    @Query(value = "select  sr from SysResource sr where sr.resourceId in (:resourceIds) ")
    List<SysResource> findByResourceId(@Param("resourceIds") String[] resourceIds);

    SysResource findByResourceUrl(String uri);

}
