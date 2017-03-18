package com.xn.performance.util;/**
 * Created by xn056839 on 2017/3/17.
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取ApplicationContext和Object的工具类
 * @author yzl
 *
 */

public class SpringContextUtils   implements ApplicationContextAware{

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtils.context = context;
    }

    public static <T> T getBean(String beanId,Class<T> clazz){
        return context.getBean(beanId, clazz);
    }

    public static ApplicationContext getContext(){
        return context;
    }

}
