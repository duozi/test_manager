package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.xn.performance.dto.PerformancePlanDto;
import com.xn.performance.dto.PerformanceResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class StartUp {
    private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
    public static Queue<PerformanceResultDto> PERFORMANCE_QUEUE=new LinkedList<PerformanceResultDto>();
    @PostConstruct
    public void getTask() {
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setIsDelete("未删除");


    }

    public void executeQueue(){

    }

}
