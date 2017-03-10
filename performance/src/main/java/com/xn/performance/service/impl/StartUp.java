package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.xn.performance.dto.PerformancePlanDto;
import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;
import com.xn.performance.service.JmeterService;
import com.xn.performance.service.PerformancePlanService;
import com.xn.performance.service.PerformanceResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class StartUp {
    private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
    public static Map<Integer, ConcurrentLinkedQueue> PERFORMANCE_Map = new HashMap<Integer, ConcurrentLinkedQueue>();
    @Autowired
    PerformanceResultService performanceResultService;

    @Autowired
    PerformancePlanService performancePlanService;
    @Autowired
    JmeterService jmeterService;

    @PostConstruct
    public void getTask() {
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setExecuteStatus("未执行");
        List<PerformancePlanShowDto> list = performanceResultService.getTask(performanceResultDto);

        jmeterService.addToQueue(list);

    }


    public void executeOnce(PerformancePlanShowDto performancePlanShowDto) {
        //更新计划的状态为执行中
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setId(performancePlanShowDto.getPlanId());
        performancePlanDto.setPlanStatus("执行中");
        performancePlanService.update(performancePlanDto);
        //更新结果的状态为执行中
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setId(performancePlanShowDto.getId());
        performanceResultDto.setExecuteStatus("执行中");
        performanceResultService.update(performanceResultDto);
    }

}
