package com.xn.interfacetest.utils;

/**
 * Created by xn058121 on 2017/2/16.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryUtil {
    private static ApplicationContext ctx_producer = null;

    public final static String ApplicationContextRoot = "classpath:";
    public final static String ApplicationContextPath = ApplicationContextRoot + "application.xml";

    public static void init() {
        if (ctx_producer == null) {
            synchronized (BeanFactoryUtil.class) {
                if(ctx_producer == null){
                    String[] configLocations = new String[]{ApplicationContextPath};
                    ctx_producer = new ClassPathXmlApplicationContext(configLocations);
                }
            }
        }
    }

    public static ApplicationContext getContext() {
        init();
        return ctx_producer;
    }
}
