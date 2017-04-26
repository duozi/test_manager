/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.mybatis.BaseDto;

import java.util.Date;


/**
 * PerformanceResult Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceResultDto extends BaseDto {
    
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
     * 执行时长 
     */
    private Integer executeTime;



    /**
     * 
     * 执行人 
     */
    private String executePerson;

    




    private Date actualStartTime;
    private Date actualStartTimeBegin;
    private Date actualStartTimeEnd;


    private Integer stressMachineId;

    private Integer stressMachineName;


    /**
     *
     * 执行状态
     */
    private String executeStatus;
    private String resultPath;

    public PerformanceResultDto(Integer id) {
        this.id = id;
    }
    public PerformanceResultDto() {

    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

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

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getSetStartTime() {
        return this.setStartTime;
    }
    
    public void setSetStartTime(Date setStartTime) {
        this.setStartTime = setStartTime;
    }

    public Integer getExecuteTime() {
        return this.executeTime;
    }
    
    public void setExecuteTime(Integer executeTime) {
        this.executeTime = executeTime;
    }

    public String getExecutePerson() {
        return this.executePerson;
    }
    
    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
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
    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
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

    @Override
    public String toString() {
        return "PerformanceResultDto{" +
                "id=" + id +
                ", planId=" + planId +
                ", setStartTime=" + setStartTime +
                ", setStartTimeBegin=" + setStartTimeBegin +
                ", setStartTimeEnd=" + setStartTimeEnd +
                ", actualEndTime=" + actualEndTime +
                ", actualEndTimeBegin=" + actualEndTimeBegin +
                ", actualEndTimeEnd=" + actualEndTimeEnd +
                ", executeTime=" + executeTime +
                ", executePerson='" + executePerson + '\'' +
                ", actualStartTime=" + actualStartTime +
                ", actualStartTimeBegin=" + actualStartTimeBegin +
                ", actualStartTimeEnd=" + actualStartTimeEnd +
                ", stressMachineId=" + stressMachineId +
                ", stressMachineName=" + stressMachineName +
                ", executeStatus='" + executeStatus + '\'' +
                ", resultPath='" + resultPath + '\'' +
                '}';
    }
}

