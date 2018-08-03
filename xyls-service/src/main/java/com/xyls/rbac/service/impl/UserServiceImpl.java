package com.xyls.rbac.service.impl;

import com.xyls.rbac.domain.*;
import com.xyls.rbac.dto.UserInfo;
import com.xyls.rbac.repository.*;
import com.xyls.rbac.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysResourceRepository sysResourceRepository;

    @Autowired
    private UserSysRoleRepository userSysRoleRepository;


    @Autowired
    private SysRoleResourceRepository  sysRoleResourceRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserInfo create(UserInfo userInfo) {

        User user=new User();
        user.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setUserName(userInfo.getUsername());
        user.setUserPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(user);

        return userInfo;
    }

    @Override
    public UserInfo getInfo(String userId) throws InvocationTargetException, IllegalAccessException {
       User user= userRepository.findByUserName(userId);
        UserInfo userInfo=null;
       if(!org.springframework.util.StringUtils.isEmpty(user)){
           userInfo=new UserInfo();

           BeanUtils.copyProperties(userInfo,user);

           userInfo.setPassword(user.getUserPassword());
       }

       return userInfo;
    }

    /**
     * 加载用户的所有信息
     * @param userId
     * @return
     */

    @Override
    public UserInfo loadUserInfo(String userId) {

        UserInfo  userInfo = new UserInfo();

        //获取用户的所有角色信息（用户角色关联表）
        List<UserSysRole> userSysRoles=userSysRoleRepository.findByUserId(userId);

        if(!CollectionUtils.isEmpty(userSysRoles)){
            String[] inRoleIds=new String[userSysRoles.size()];
            int i=0;
            for(UserSysRole userSysRole:userSysRoles){
                inRoleIds[i]= userSysRole.getRoleId();
                i++;
            }

            List<SysRole> sysRoles=sysRoleRepository.findByRoleId(inRoleIds);
            /**
             * 添加到用户信息集合里
             */
            userInfo.setSysRoles(sysRoles);

            //查询用户的角色资源信息（角色资源关联表）
           List<SysRoleResource> sysRoleResources=sysRoleResourceRepository.findByRoleId(inRoleIds);

           if(!CollectionUtils.isEmpty(sysRoleResources)){
                String[] inResourceIds=new String[sysRoleResources.size()];
                i=0;
                for (SysRoleResource sysRoleResource:sysRoleResources){
                    inResourceIds[i]=sysRoleResource.getResourceId();
                    i++;
                }

                //查询资源信息
                List<SysResource> sysResources=sysResourceRepository.findByResourceId(inResourceIds);

               /**
                * 将权限加到集合中
                */
               userInfo.setSysResources(sysResources);

           }
        }

        return userInfo;

    }

}
