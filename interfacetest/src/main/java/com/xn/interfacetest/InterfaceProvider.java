package com.xn.interfacetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xn058121 on 2017/2/22.
 */
public class InterfaceProvider {
    private static final Logger logger = LoggerFactory.getLogger(InterfaceProvider.class);
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/spring-context.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
