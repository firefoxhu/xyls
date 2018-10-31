/**
 *
 */
package com.xyls.rbac.authentication;

import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhailiang
 */
@Component
@Transactional
@Slf4j
public class RbacUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名:" + username);

        UserInfo userInfo = null;
        try {
            userInfo = userService.getInfo(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userInfo == null)
            throw new UsernameNotFoundException("用户名不存在");
        //加载 角色 权限 信息 放在成功登录后处理
        UserInfo principal = userService.loadUserInfo(userInfo.getUserId());
        principal.setUserName(username);
        principal.setPassword(userInfo.getPassword());

        return principal;
    }

}
