package com.xly.mall.common.base.db;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("springContextHolder")
public class SpringContextHolder implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    public SpringContextHolder() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
//        SqlSessionFactoryBean sqlSessionFactoryBean = applicationContext.getBean(SqlSessionFactoryBean.class);
//        InterceptorUtil.setSqlSessionFactoryBean((SqlSessionFactoryBean) SpringContextHolder.applicationContext.getBean(SqlSessionFactoryBean.class));
//        InterceptorUtil.dynamicAddInterceptor("com.xly.mall.common.base.result.SqlLogInterceptor");
    }
}
