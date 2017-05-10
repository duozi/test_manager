package com.xn.performance;/**
 * Created by xn056839 on 2017/2/22.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    private static final Logger logger = LoggerFactory.getLogger(Provider.class);
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/spring-context.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
