package com.xyls.service;

import com.xyls.dto.form.ShopForm;
import com.xyls.dto.form.UserForm;
import com.xyls.model.Shop;
import com.xyls.rbac.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;

/**
 * 与权限管理的userService分开写
 */
public interface UserAdminService {

    /**
     获取所有的用户信息
     * @param pageable
     * @return
     */
    Page<User> query(Pageable pageable);

    /**
     * 新增用户
     * @param userForm
     * @param userId
     */
    void save(UserForm userForm,String userId) throws Exception;

    /**
     * 删除一条或者多条信息
     * @param ids
     */
    void  remove(String ids);


    /**
     * 修改信息
     */
    void  modify(UserForm userForm, String userId) throws Exception;

    /**
     * 根据id查找
     */
    User query(String id);


    /**
     * 根据id查找包括用户关联的角色信息
     */
    UserForm queryWithRoles(String id) throws InvocationTargetException, IllegalAccessException;

}
