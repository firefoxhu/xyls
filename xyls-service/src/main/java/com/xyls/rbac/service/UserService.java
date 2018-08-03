package com.xyls.rbac.service;
import com.xyls.rbac.dto.UserInfo;

import java.lang.reflect.InvocationTargetException;

public interface UserService {
    /**
     * 创建管理员
     * @param userInfo
     * @return
     */
    UserInfo create(UserInfo userInfo);

    /**
     * 获取管理员详细信息
     * @param userId
     * @return
     */
    UserInfo getInfo(String userId) throws InvocationTargetException, IllegalAccessException;


    UserInfo loadUserInfo(String userId);


}
