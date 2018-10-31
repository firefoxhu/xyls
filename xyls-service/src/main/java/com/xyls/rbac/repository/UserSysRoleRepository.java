package com.xyls.rbac.repository;

import com.xyls.rbac.domain.UserSysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSysRoleRepository extends XylsRepository<UserSysRole> {

    @Query(value = "select ur from UserSysRole ur where ur.userId=:userId ")
    List<UserSysRole> findByUserId(@Param("userId") String userId);

    void deleteByUserId(String userId);
}
