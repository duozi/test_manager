package com.xn;

import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.plan.service.impl.PlanServiceImpl;
import com.xn.autotest.database.PooledDbSource;
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
@ContextConfiguration({"classpath:/spring/spring-context.xml",}) //加载配置文件
public class PlanTest {
//        @Resource
//        DbPropertiesServiceImpl dbPropertiesService;
    @Resource
    PlanServiceImpl planService;
    @Resource
    PooledDbSource pooledDbSource;
//    @Resource
//    DBPropertiesDao dbPropertiesDao;
//    @Resource
//DbPropertiesMapper dbPropertiesMapper;

//    @Test
//    public void Dbtest() {
//        DbProperties dbProperties = new DbProperties();
//        dbProperties.setId(1);
//        DbPropertiesDto dbPropertiesDto = dbPropertiesService.get(dbProperties);
//    }


    @Test
    public void plantest() {
pooledDbSource.getDataBaseType("abc");
       List<PlanDto> planDto =planService.list();
    }
//
//    @Test
//    public void test() {
//        List<DBProperties> dbPropertiesList = dbPropertiesDao.getAllDBProperties();
//    }

//    @Test
//    public void test2() {
//        DbProperties dbProperties = new DbProperties();
////        dbProperties.setId(1);
//        List<DbProperties> dbPropertiesList  = dbPropertiesMapper.list(null);
//    }

}