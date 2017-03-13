package com.xn.performance;

import com.xn.performance.dto.PerformanceScenarioDto;
import com.xn.performance.service.PerformanceScenarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.xn.performance.service.impl.StartUp.generateJmeterScript;

/**
 * Created by xn056839 on 2017/3/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class StartUpTest {
    @Autowired
    PerformanceScenarioService performanceScenarioService;
    @Test
    public void generateJmeterScriptTest() throws Exception {
        PerformanceScenarioDto performanceScenarioDto=new PerformanceScenarioDto();
        performanceScenarioDto.setId(11);
        performanceScenarioDto=performanceScenarioService.get(performanceScenarioDto);
        generateJmeterScript("E:\\upload\\线程组.jmx", performanceScenarioDto);
    }

}