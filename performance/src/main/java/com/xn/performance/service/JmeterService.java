package com.xn.performance.service;

import com.xn.performance.dto.PerformanceResultDto;

/**
 * Created by xn056839 on 2017/3/7.
 */

public interface JmeterService {
    void execute(String stressMachineIp) ;
    void executePlan(PerformanceResultDto performanceResultDto) ;

    boolean stopPlan(String ip);
}
