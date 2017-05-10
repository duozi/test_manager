package com.xn.performance.entity;/**
 * Created by xn056839 on 2017/3/7.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceExecute {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceExecute.class);
    private Integer planId;
    private Integer stressMachineId;
    private String executeTime;

    public PerformanceExecute(Integer planId, Integer stressMachineId, String executeTime) {
        this.planId = planId;
        this.stressMachineId = stressMachineId;
        this.executeTime = executeTime;
    }

    public PerformanceExecute(Integer planId, Integer stressMachineId) {
        this.planId = planId;
        this.stressMachineId = stressMachineId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getStressMachineId() {
        return stressMachineId;
    }

    public void setStressMachineId(Integer stressMachineId) {
        this.stressMachineId = stressMachineId;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }
}
