/**
 *
 */
package com.xyls.rbac.service.impl;

import com.xyls.rbac.domain.SysResource;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.service.RbacService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhailiang
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;

        if (principal instanceof UserInfo) {

            UserInfo userInfo = (UserInfo) principal;

            //如果用户名是admin，就永远返回true
            if (StringUtils.equals(userInfo.getUsername(), "admin")) {
                hasPermission = true;
            } else {
                // 读取用户所拥有权限的所有URL
                List<SysResource> urls = userInfo.getSysResources();
                for (SysResource url : urls) {
                    if (antPathMatcher.match(url.getResourceUrl(), request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        return hasPermission;
    }

}
