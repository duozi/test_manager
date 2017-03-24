package com.xn.performance.service;

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;

import java.util.List;

/**
 * Created by xn056839 on 2017/3/7.
 */

public interface JmeterService {
    String execute(String stressMachineIp,String jmeterScriptPath,Integer id);

    void executePlan(String executeType, PerformanceResultDto performanceResultDto);

    void addToNowQueue(List<PerformancePlanShowDto> list);

    void addToScheduleQueue(List<PerformancePlanShowDto> list);

    void stopPlan(Integer id,Integer planId);

    void executeOnce(PerformancePlanShowDto performancePlanShowDto);
}
