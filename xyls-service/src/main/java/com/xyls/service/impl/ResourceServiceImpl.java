package com.xyls.service.impl;

import com.xyls.dto.form.ResourceForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.rbac.domain.SysResource;
import com.xyls.rbac.repository.SysResourceRepository;
import com.xyls.service.ResourceService;
import com.xyls.utils.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private SysResourceRepository resourceRepository;

    @Override
    public Page<SysResource> query(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    @Override
    public void save(ResourceForm resourceForm, String userId) throws Exception {
        //验证门店是否已经存在
        SysResource existResource = resourceRepository.findByResourceUrl(resourceForm.getResourceUrl());
        if (!StringUtils.isEmpty(existResource)) {
            throw new Exception("资源已存在！");
        }
        SysResource sysResource = new SysResource();
        BeanUtils.copyProperties(sysResource,resourceForm);
        sysResource.setResourceId(resourceForm.getResourceId());
        sysResource.setCreateTime(DateUtil.todayDateTime());
        resourceRepository.save(sysResource);
    }

    @Override
    public void remove(String ids) {
        if(org.apache.commons.lang3.StringUtils.isEmpty(ids)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        for(String item:ids.split(",") ){
            resourceRepository.delete(item);
        }
    }

    @Override
    public void modify(ResourceForm resourceForm, String userId) throws Exception {
        if (StringUtils.isEmpty(resourceForm.getResourceId())) {
            throw new Exception("参数非法请刷新页面重试！");
        }

        //验证门店是否已经存在
        SysResource existResource = resourceRepository.findByResourceUrl(resourceForm.getResourceUrl());
        if (!StringUtils.isEmpty(existResource)) {
            throw new Exception("资源已存在！");
        }

        SysResource sysResource =resourceRepository.findOne(resourceForm.getResourceId());

        BeanUtils.copyProperties(sysResource,resourceForm);
        resourceRepository.save(sysResource);
    }

    @Override
    public SysResource query(String id) {
        return resourceRepository.findOne(id);
    }

    @Override
    public void batch(List<SysResource> sysResources) {
        List<SysResource> sysResourceList = new ArrayList<>();
        //过滤集合过滤以有的菜单
        for(SysResource item: sysResources){
            SysResource sysResource = resourceRepository.findByResourceUrl(item.getResourceUrl());
            if(StringUtils.isEmpty(sysResource)){
                sysResourceList.add(item);
            }
        }

        resourceRepository.save(sysResourceList);
    }
}
