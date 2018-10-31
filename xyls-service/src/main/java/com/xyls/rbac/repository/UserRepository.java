package com.xyls.rbac.repository;

import com.xyls.rbac.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends XylsRepository<User> {

    User findByUserName(String username);

}
