package com.xn;

import com.xn.autotest.bean.properties.DBProperties;
import com.xn.autotest.dao.DBPropertiesDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xn056839 on 2016/12/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/spring/spring-context.xml",}) //加载配置文件
public class PooledDataSourceTest {
    @Resource
    DBPropertiesDao dbPropertiesDao;
    @Test
    public void DBtest() {
        List<DBProperties> dbPropertiesList=dbPropertiesDao.getAllDBProperties();
        System.out.println("");
    }
}