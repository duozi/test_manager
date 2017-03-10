package com.xn.performance.service;

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;

import java.util.List;

/**
 * Created by xn056839 on 2017/3/7.
 */

public interface JmeterService {
    void execute(String stressMachineIp);

    void executePlan(String executeType, PerformanceResultDto performanceResultDto);

    void addToQueue(List<PerformancePlanShowDto> list);

    boolean stopPlan(String ip);
}
