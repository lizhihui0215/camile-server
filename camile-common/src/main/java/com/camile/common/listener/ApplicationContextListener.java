package com.camile.common.listener;

import com.camile.common.annotation.InitService;
import com.camile.common.base.Interface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger _log = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // root application context
        if(null == contextRefreshedEvent.getApplicationContext().getParent()) {
            _log.debug(">>>>> spring初始化完毕 <<<<<");
            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(InitService.class);
            for(Object service : baseServices.values()) {
                _log.debug(">>>>> {}.initMapper()", service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    _log.error("初始化Service的initMapper方法异常", e);
                    e.printStackTrace();
                }
            }

            // 系统入口初始化
            Map<String, Interface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(Interface.class);
            for(Object service : baseInterfaceBeans.values()) {
                _log.debug(">>>>> {}.init()", service.getClass().getName());
                try {
                    Method init = service.getClass().getMethod("init");
                    init.invoke(service);
                } catch (Exception e) {
                    _log.error("初始化Interface的init方法异常", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
