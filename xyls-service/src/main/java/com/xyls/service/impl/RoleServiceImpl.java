package com.xyls.service.impl;

import com.xyls.dto.dto.ResourceTree;
import com.xyls.dto.dto.RoleMenuDTO;
import com.xyls.dto.form.RoleForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.domain.SysResource;
import com.xyls.rbac.domain.SysRole;
import com.xyls.rbac.domain.SysRoleResource;
import com.xyls.rbac.repository.SysResourceRepository;
import com.xyls.rbac.repository.SysRoleRepository;
import com.xyls.rbac.repository.SysRoleResourceRepository;
import com.xyls.service.RoleService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysRoleResourceRepository sysRoleResourceRepository;

    @Autowired
    private SysResourceRepository sysResourceRepository;

    @Override
    public Page<SysRole> query(Pageable pageable) {
        return sysRoleRepository.findAll(pageable);
    }

    @Override
    public void save(RoleForm roleForm, String userId) throws Exception {
        //验证门店是否已经存在
        SysRole existRole = sysRoleRepository.findByRoleName(roleForm.getRoleName());
        if (!StringUtils.isEmpty(existRole)) {
            throw new Exception("角色已存在！");
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRole, roleForm);
        sysRole.setRoleId(GenKeyUtil.key());
        sysRole.setCreateTime(DateUtil.todayDateTime());

        sysRoleRepository.save(sysRole);
    }

    @Override
    public void remove(String ids) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(ids)) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "id为空异常刷新页面重试！");
        }
        for (String item : ids.split(",")) {
            sysRoleRepository.delete(item);
        }
    }

    @Override
    public void modify(RoleForm roleForm, String userId) throws Exception {
        if (StringUtils.isEmpty(roleForm.getRoleId())) {
            throw new Exception("参数非法请刷新页面重试！");
        }

        SysRole sysRole = sysRoleRepository.findOne(roleForm.getRoleId());

        BeanUtils.copyProperties(sysRole, roleForm);
        sysRoleRepository.save(sysRole);
    }

    @Override
    public SysRole query(String id) {
        return sysRoleRepository.findOne(id);
    }

    @Override
    public RoleMenuDTO roleBindResource(String roleId) throws InvocationTargetException, IllegalAccessException {
        SysRole sysRole = sysRoleRepository.findOne(roleId);

        List<SysRoleResource> sysRoleResources = sysRoleResourceRepository.findByRoleId(new String[]{roleId});

        List<SysResource> sysRoleResourceList = sysResourceRepository.findAll();

        RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
        roleMenuDTO.setRoleId(sysRole.getRoleId());
        roleMenuDTO.setRoleName(sysRole.getRoleName());

        Map<String, List<ResourceTree>> map = new HashMap<>();

        //组装数据树
        for (SysResource sysResource : sysRoleResourceList) {


            if (org.apache.commons.lang3.StringUtils.equals("root", sysResource.getResourceParentId())) {

                List<ResourceTree> resourceTrees = new ArrayList<>();

                for (SysResource item : sysRoleResourceList) {

                    if (org.apache.commons.lang3.StringUtils.equals(item.getResourceParentId(), sysResource.getResourceId())) {

                        ResourceTree resourceTree = new ResourceTree();

                        BeanUtils.copyProperties(resourceTree, item);

                        for (SysRoleResource sysRoleResource : sysRoleResources) {

                            if (org.apache.commons.lang3.StringUtils.equals(sysRoleResource.getResourceId(), item.getResourceId())) {

                                resourceTree.setHave(true);

                                break;
                            }
                        }
                        resourceTrees.add(resourceTree);
                    }
                }
                map.put(sysResource.getResourceName(), resourceTrees);
            }
        }
        roleMenuDTO.setMap(map);

        return roleMenuDTO;
    }

    @Override
    public void bind(String roleId, String resourceIds) {

        String[] ids = resourceIds.split(",");
        List<SysRoleResource> sysRoleResources = new ArrayList<>();
        for (String item : ids) {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(roleId);
            sysRoleResource.setResourceId(item);
            sysRoleResource.setRoleResourceId(GenKeyUtil.key());
            sysRoleResources.add(sysRoleResource);
        }

        sysRoleResourceRepository.save(sysRoleResources);
    }

    @Override
    @Transactional
    public void unbind(String roleId, String resourceIds) {

        String[] ids = resourceIds.split(",");
        for (String item : ids) {
            sysRoleResourceRepository.deleteByRoleIdAndResourceId(roleId, item);
        }

    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleRepository.findAll();
    }


}
