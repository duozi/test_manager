package com.xn.performance.service;

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;

import java.util.List;

/**
 * Created by xn056839 on 2017/3/7.
 */

public interface JmeterService {
    void execute(String stressMachineIp,String jmeterScriptPath);

    void executePlan(String executeType, PerformanceResultDto performanceResultDto);

    void addToNowQueue(List<PerformancePlanShowDto> list);

    void addToScheduleQueue(List<PerformancePlanShowDto> list);

    boolean stopPlan(String ip);

    void executeOnce(PerformancePlanShowDto performancePlanShowDto);
}
