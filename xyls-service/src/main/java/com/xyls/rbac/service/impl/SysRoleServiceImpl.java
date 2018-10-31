package com.xyls.rbac.service.impl;

import com.xyls.rbac.domain.SysRole;
import com.xyls.rbac.dto.SysRoleInfo;
import com.xyls.rbac.repository.SysRoleRepository;
import com.xyls.rbac.service.SysRoleService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;


@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;


    @Override
    public SysRoleInfo create(SysRoleInfo sysRoleInfo) {

        SysRole sysRole = new SysRole();
        sysRole.setRoleId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysRole.setRoleName(sysRoleInfo.getRoleName());
        sysRoleRepository.save(sysRole);
        return sysRoleInfo;
    }

    @Override
    public SysRoleInfo getInfo(String roleId) throws InvocationTargetException, IllegalAccessException {

        SysRole sysRole = sysRoleRepository.findOne(roleId);

        SysRoleInfo sysRoleInfo = new SysRoleInfo();
        BeanUtils.copyProperties(sysRoleInfo, sysRole);

        return sysRoleInfo;
    }
}
