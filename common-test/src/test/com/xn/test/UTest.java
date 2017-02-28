package com.xn.test;

import com.xn.common.utils.PropertyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring/spring-context.xml"})
public class UTest {
    private static final Logger logger = LoggerFactory.getLogger(UTest.class);

    @Test
    public void test(){
        String filePath = PropertyUtil.getProperty("upload_path") + "ddd";
        System.out.println(filePath);
    }
}

