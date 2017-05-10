/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.entity;

import com.xn.performance.util.BaseEntity;

import java.util.Date;

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
    private Date setStartTimeBegin;
    private Date setStartTimeEnd;



    /**
     * 
     * 实际结束时间
     */
    private Date actualEndTime;
    private Date actualEndTimeBegin;
    private Date actualEndTimeEnd;



    /**
     * 
     * 实际开始时间 
     */
    private Date actualStartTime;
    private Date actualStartTimeBegin;
    private Date actualStartTimeEnd;

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
     *
     * 执行状态
     */
    private String executeStatus;



    private Integer stressMachineId;
    private  String stressMachineName;
    private  String resultPath;

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public Date getSetStartTimeBegin() {
        return setStartTimeBegin;
    }

    public void setSetStartTimeBegin(Date setStartTimeBegin) {
        this.setStartTimeBegin = setStartTimeBegin;
    }

    public Date getSetStartTimeEnd() {
        return setStartTimeEnd;
    }

    public void setSetStartTimeEnd(Date setStartTimeEnd) {
        this.setStartTimeEnd = setStartTimeEnd;
    }

    public Date getActualEndTimeBegin() {
        return actualEndTimeBegin;
    }

    public void setActualEndTimeBegin(Date actualEndTimeBegin) {
        this.actualEndTimeBegin = actualEndTimeBegin;
    }

    public Date getActualEndTimeEnd() {
        return actualEndTimeEnd;
    }

    public void setActualEndTimeEnd(Date actualEndTimeEnd) {
        this.actualEndTimeEnd = actualEndTimeEnd;
    }

    public Date getActualStartTimeBegin() {
        return actualStartTimeBegin;
    }

    public void setActualStartTimeBegin(Date actualStartTimeBegin) {
        this.actualStartTimeBegin = actualStartTimeBegin;
    }

    public Date getActualStartTimeEnd() {
        return actualStartTimeEnd;
    }

    public void setActualStartTimeEnd(Date actualStartTimeEnd) {
        this.actualStartTimeEnd = actualStartTimeEnd;
    }

    public Integer getStressMachineId() {
        return stressMachineId;
    }

    public void setStressMachineId(Integer stressMachineId) {
        this.stressMachineId = stressMachineId;
    }

    public String getStressMachineName() {
        return stressMachineName;
    }

    public void setStressMachineName(String stressMachineName) {
        this.stressMachineName = stressMachineName;
    }

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
    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }
    public Date getSetStartTime() {
        return setStartTime;
    }

    public void setSetStartTime(Date setStartTime) {
        this.setStartTime = setStartTime;
    }
}
