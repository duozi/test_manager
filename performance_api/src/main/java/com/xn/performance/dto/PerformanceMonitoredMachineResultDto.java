/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.mybatis.BaseDto;

/**
 * PerformanceMonitoredMachineResult Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceMonitoredMachineResultDto extends BaseDto {
    
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
     * 请求总数 
     */
    private Integer samples;

    /**
     * 
     * 平均响应时长 
     */
    private Integer average;

    /**
     * 
     * 最小响应时长 
     */
    private Integer minTime;

    /**
     * 
     * 最大响应时长 
     */
    private Integer maxTime;

    /**
     * 
     * 错误请求总数 
     */
    private Integer error;

    /**
     * 
     * 每秒完成的请求数 
     */
    private Integer throughput;

    /**
     * 
     * 每秒从服务器端接受到的数据量 
     */
    private Integer kbsec;

    /**
     * 
     * 负载 
     */
    private Integer machineLoad;

    /**
     * 
     * qps 
     */
    private Integer qps;

    /**
     * 
     * 内存 
     */
    private Integer memory;


    private Integer planId;
    private Integer resultId;
    private  String monitoredMachineName;
    private  Integer monitoredMachineId;
    private String monitoredMachineIp;

    public String getMonitoredMachineIp() {
        return monitoredMachineIp;
    }

    public void setMonitoredMachineIp(String monitoredMachineIp) {
        this.monitoredMachineIp = monitoredMachineIp;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public String getMonitoredMachineName() {
        return monitoredMachineName;
    }

    public void setMonitoredMachineName(String monitoredMachineName) {
        this.monitoredMachineName = monitoredMachineName;
    }

    public Integer getMonitoredMachineId() {
        return monitoredMachineId;
    }

    public void setMonitoredMachineId(Integer monitoredMachineId) {
        this.monitoredMachineId = monitoredMachineId;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }
    
    public Integer getSamples() {
        return this.samples;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }
    
    public Integer getAverage() {
        return this.average;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }
    
    public Integer getMinTime() {
        return this.minTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }
    
    public Integer getMaxTime() {
        return this.maxTime;
    }

    public void setError(Integer error) {
        this.error = error;
    }
    
    public Integer getError() {
        return this.error;
    }

    public void setThroughput(Integer throughput) {
        this.throughput = throughput;
    }
    
    public Integer getThroughput() {
        return this.throughput;
    }

    public void setKbsec(Integer kbsec) {
        this.kbsec = kbsec;
    }
    
    public Integer getKbsec() {
        return this.kbsec;
    }

    public void setMachineLoad(Integer machineLoad) {
        this.machineLoad = machineLoad;
    }
    
    public Integer getMachineLoad() {
        return this.machineLoad;
    }

    public void setQps(Integer qps) {
        this.qps = qps;
    }
    
    public Integer getQps() {
        return this.qps;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }
    
    public Integer getMemory() {
        return this.memory;
    }



}

