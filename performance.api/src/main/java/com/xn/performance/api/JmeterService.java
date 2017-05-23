package com.xn.performance.api;

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;

import java.util.List;

/**
 * Created by xn056839 on 2017/3/7.
 */

public interface JmeterService {
    String execute(String stressMachineIp, String jmeterScriptPath, String scriptDependenceFile,Integer scriptId,Integer id) throws Exception;

    void executePlan(String executeType, PerformanceResultDto performanceResultDto);

    void addToNowQueue(List<PerformancePlanShowDto> list);

    void addToNowQueue(PerformancePlanShowDto performancePlanShowDto);

    void addToScheduleQueue(List<PerformancePlanShowDto> list);

    void scheduleJob(PerformancePlanShowDto performancePlanShowDto);

    void addToScheduleQueue(PerformancePlanShowDto performancePlanShowDto);

    void stopPlan(Integer id, Integer planId,String executeStatus);

    void executeOnce(PerformancePlanShowDto performancePlanShowDto) ;
}
