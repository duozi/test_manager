/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;

import com.xn.performance.util.BaseDto;

import java.util.Date;


/**
 * PerformanceResult Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceResultDto extends BaseDto{
    
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
     * 执行人 
     */
    private String executePerson;

    
    /**
     * 设置执行时间，立即执行的就是00000000
     */
    private Date setStartTimeBegin;

    /**
     * 设置执行时间，立即执行的就是00000000
     */
    private Date setStartTimeEnd;

    /**
     * 实际开始时间
     */
    private Date actualStartTimeBegin;

    /**
     * 实际开始时间
     */
    private Date actualStartTimeEnd;

    private Integer stressMachineId;

    private Integer stressMachineName;

    /**
     *
     * 执行状态
     */
    private String executeStatus;

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Integer getStressMachineId() {
        return stressMachineId;
    }

    public void setStressMachineId(Integer stressMachineId) {
        this.stressMachineId = stressMachineId;
    }

    public Integer getStressMachineName() {
        return stressMachineName;
    }

    public void setStressMachineName(Integer stressMachineName) {
        this.stressMachineName = stressMachineName;
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

    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
    }
    
    public String getExecutePerson() {
        return this.executePerson;
    }


    public Date getSetStartTimeBegin() {
        return this.setStartTimeBegin;
    }
    
    public void setSetStartTimeBegin(Date setStartTimeBegin) {
        this.setStartTimeBegin = setStartTimeBegin;
    }    
    
    public Date getSetStartTimeEnd() {
        return this.setStartTimeEnd;
    }
    
    public void setSetStartTimeEnd(Date setStartTimeEnd) {
        this.setStartTimeEnd = setStartTimeEnd;
    }
    
    public Date getActualStartTimeBegin() {
        return this.actualStartTimeBegin;
    }
    
    public void setActualStartTimeBegin(Date actualStartTimeBegin) {
        this.actualStartTimeBegin = actualStartTimeBegin;
    }    
    
    public Date getActualStartTimeEnd() {
        return this.actualStartTimeEnd;
    }
    
    public void setActualStartTimeEnd(Date actualStartTimeEnd) {
        this.actualStartTimeEnd = actualStartTimeEnd;
    }
    

}

