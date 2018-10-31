package com.xyls.rbac.scanmenu;

import com.xyls.rbac.annotation.Menu;
import com.xyls.rbac.assemble.AssembleResource;
import com.xyls.rbac.domain.SysResource;
import com.xyls.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InitResourceDataConfigurer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ResourceService resourceService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> map = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(Menu.class);

        for (String key : map.keySet()) {
            Class<?> clazz = map.get(key).getClass();
            List<SysResource> sysResources = AssembleResource.assembleAnnotationMenuData(clazz);
            resourceService.batch(sysResources);
        }
    }
}
