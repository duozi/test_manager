package com.xn;/**
 * Created by xn056839 on 2016/12/26.
 */

import com.xn.autotest.bean.properties.dbProperties.dto.DbPropertiesDto;
import com.xn.autotest.bean.properties.dbProperties.entity.DbProperties;
import com.xn.autotest.bean.properties.dbProperties.service.impl.DbPropertiesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by xn056839 on 2016/12/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/spring/spring-context.xml",}) //加载配置文件
public class Dbtest {
    private static final Logger logger = LoggerFactory.getLogger(Dbtest.class);
    @Resource
    DbPropertiesServiceImpl dbPropertiesService;
    @Test
    public void Dbtest() {
        DbProperties dbProperties = new DbProperties();
        dbProperties.setId(1);
        DbPropertiesDto dbPropertiesDto = dbPropertiesService.get(dbProperties);
    }
//    @Test
//    public void test(){
//        System.out.println("");
//    }
}
