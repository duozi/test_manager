/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.util.BaseDto;

/**
 * PerformancePlanMonitored Dto 对象
 * 
 * @author zhouxi
 * @date 2017-03-01
 */
public class PerformancePlanMonitoredDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * id 
     */
    private Integer id;

    /**
     * 
     * 计划id 
     */
    private Integer planId;

    /**
     * 
     * 监控机id 
     */
    private Integer monitoredMachineId;

    /**
     * 
     * 监控机名 
     */
    private String monitoredMachineName;
    private String monitoredMachineIp;

    public String getMonitoredMachineIp() {
        return monitoredMachineIp;
    }

    public void setMonitoredMachineIp(String monitoredMachineIp) {
        this.monitoredMachineIp = monitoredMachineIp;
    }



    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    
    public Integer getPlanId() {
        return this.planId;
    }

    public void setMonitoredMachineId(Integer monitoredMachineId) {
        this.monitoredMachineId = monitoredMachineId;
    }
    
    public Integer getMonitoredMachineId() {
        return this.monitoredMachineId;
    }

    public void setMonitoredMachineName(String monitoredMachineName) {
        this.monitoredMachineName = monitoredMachineName;
    }
    
    public String getMonitoredMachineName() {
        return this.monitoredMachineName;
    }



}

