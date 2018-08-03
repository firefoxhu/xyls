package com.xyls.rbac.assemble;

import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.domain.SysResource;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AssembleResource {


    public static List<SysResource> assembleAnnotationMenuData(Class<?> clazz){

        List<SysResource> sysResources = new ArrayList<>();

        //类上的注解
        Menu parentMenu = clazz.getAnnotation(Menu.class);
        //主菜单
        SysResource parentResource = new SysResource();
        String parentId = GenKeyUtil.key();
        parentResource.setResourceId(parentId);
        parentResource.setResourceName(parentMenu.name());
        parentResource.setResourceUrl(parentMenu.uri());
        parentResource.setResourceType(parentMenu.type().toString());
        parentResource.setDescription(parentMenu.description());
        parentResource.setCreateTime(DateUtil.todayDateTime());
        parentResource.setResourceParentId("root");
        sysResources.add(parentResource);
        //子菜单
        Method[] methods = clazz.getMethods();

        for(Method m:methods){
            if(m.isAnnotationPresent(Menu.class)){
                Menu menu = m.getAnnotation(Menu.class);
                SysResource childResource = new SysResource();
                childResource.setResourceId(GenKeyUtil.key());
                childResource.setResourceName(menu.name());

                if(menu.uri().equals("main")){
                    System.out.println(1);
                }

                childResource.setResourceUrl(parentResource.getResourceUrl()+menu.uri());
                childResource.setResourceType(menu.type().toString());
                childResource.setDescription(menu.description());
                childResource.setCreateTime(DateUtil.todayDateTime());
                childResource.setResourceParentId(parentId);
                sysResources.add(childResource);
            }
        }
        return sysResources;
    }

}
