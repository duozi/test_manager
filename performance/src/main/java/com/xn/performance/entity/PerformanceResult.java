/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.entity;

import java.util.Date;
import com.xn.performance.util.BaseEntity;

/**
 * PerformanceResult 实体类
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceResult extends BaseEntity {

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
     * 设置执行时间，立即执行的就是00000000 
     */
    private Date setStartTime;

    /**
     * 
     * 实际开始时间 
     */
    private Date actualStartTime;

    /**
     * 
     * 执行时长 
     */
    private Integer executeTime;

    /**
     * 
     * 监控机id 
     */
    private Integer monitoredMachineId;

    /**
     * 
     * 监控机结果id 
     */
    private Integer monitoredMachineResultId;

    /**
     * 
     * 执行人 
     */
    private String executePerson;


    
    private PerformancePlan performancePlan;
   
    
    private PerformanceMonitoredMachineResult performanceMonitoredMachineResult;
   
    
    private PerformanceMonitoredMachine performanceMonitoredMachine;
   
	
	public PerformanceResult(){
	    // default constructor
	}
    
    public PerformanceResult(Integer id){
        this.id = id;
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
    
    public void setSetStartTime(Date setStartTime) {
        this.setStartTime = setStartTime;
    }
    
    public Date getSetStartTime() {
        return this.setStartTime;
    }
    
    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }
    
    public Date getActualStartTime() {
        return this.actualStartTime;
    }
    
    public void setExecuteTime(Integer executeTime) {
        this.executeTime = executeTime;
    }
    
    public Integer getExecuteTime() {
        return this.executeTime;
    }
    
    public void setMonitoredMachineId(Integer monitoredMachineId) {
        this.monitoredMachineId = monitoredMachineId;
    }
    
    public Integer getMonitoredMachineId() {
        return this.monitoredMachineId;
    }
    
    public void setMonitoredMachineResultId(Integer monitoredMachineResultId) {
        this.monitoredMachineResultId = monitoredMachineResultId;
    }
    
    public Integer getMonitoredMachineResultId() {
        return this.monitoredMachineResultId;
    }
    
    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
    }
    
    public String getExecutePerson() {
        return this.executePerson;
    }
    


    
    public void setPerformancePlan(PerformancePlan performancePlan){
        this.performancePlan = performancePlan;
    }
    
    public PerformancePlan getPerformancePlan() {
        return performancePlan;
    }
    
    public void setPerformanceMonitoredMachineResult(PerformanceMonitoredMachineResult performanceMonitoredMachineResult){
        this.performanceMonitoredMachineResult = performanceMonitoredMachineResult;
    }
    
    public PerformanceMonitoredMachineResult getPerformanceMonitoredMachineResult() {
        return performanceMonitoredMachineResult;
    }
    
    public void setPerformanceMonitoredMachine(PerformanceMonitoredMachine performanceMonitoredMachine){
        this.performanceMonitoredMachine = performanceMonitoredMachine;
    }
    
    public PerformanceMonitoredMachine getPerformanceMonitoredMachine() {
        return performanceMonitoredMachine;
    }
}
