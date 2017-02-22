/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;

import com.xn.performance.util.BaseDto;


/**
 * PerformanceMonitoredMachineResult Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceMonitoredMachineResultDto extends BaseDto{
    
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
    private Integer min;

    /**
     * 
     * 最大响应时长 
     */
    private Integer max;

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
    private Integer load;

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

    public void setMin(Integer min) {
        this.min = min;
    }
    
    public Integer getMin() {
        return this.min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
    
    public Integer getMax() {
        return this.max;
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

    public void setLoad(Integer load) {
        this.load = load;
    }
    
    public Integer getLoad() {
        return this.load;
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

