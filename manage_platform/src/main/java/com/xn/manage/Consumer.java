package com.xn.manage;/**
 * Created by xn056839 on 2017/2/22.
 */

import com.xn.performance.api.PerformanceStressMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/dubbo/dubbo-consumer.xml"});
        context.start();

        PerformanceStressMachineService demoService = (PerformanceStressMachineService) context.getBean("performanceStressMachineService"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法

        System.out.println( hello ); // 显示调用结果
    }
}
