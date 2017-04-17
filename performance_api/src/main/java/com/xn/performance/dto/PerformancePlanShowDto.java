/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.mybatis.BaseDto;

import java.util.Date;
import java.util.List;


/**
 * PerformancePlan Dto 对象
 *
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformancePlanShowDto extends BaseDto {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 结果id
     */
    private Integer id;

    private Integer planId;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    /**
     * 计划名
     */
    private String planName;

    /**
     * 计划描述
     */
    private String remark;

    /**
     * 公司
     */
    private String company;

    /**
     * 部门
     */
    private String department;

    /**
     * 系统
     */
    private String psystem;

    /**
     * 脚本id
     */
    private Integer scriptId;
    /**
     * 脚本名称
     */
    private String scriptName;
    /**
     * 场景id
     */
    private Integer scenarioId;
    /**
     * 场景名称
     */
    private String scenarioName;


    /**
     * 计划状态
     */
    private String planStatus;

    /**
     * 创建人
     */
    private String createPerson;


    /**
     * 是否已删除
     */

    private String isDelete;
    /**
     * 压力机id
     */
    private Integer stressMachineId;
    /**
     * 压力机名称
     */
    private String stressMachineName;

    /**
     * 监控机列表
     */
    private List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList;
    /**
     * 实际开始时间
     */
    private Date actualStartTime;

    private String executeStatus;

    private Date setStartTime;
    private String resultPath;
    private String executePerson;
    private Date actualStartTimeBegin;
    private Date actualStartTimeEnd;

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

    public String getExecutePerson() {
        return executePerson;
    }

    public void setExecutePerson(String executePerson) {
        this.executePerson = executePerson;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public Date getSetStartTime() {
        return setStartTime;
    }

    public void setSetStartTime(Date setStartTime) {
        this.setStartTime = setStartTime;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public List<PerformancePlanMonitoredDto> getPerformancePlanMonitoredDtoList() {
        return performancePlanMonitoredDtoList;
    }

    public void setPerformancePlanMonitoredDtoList(List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList) {
        this.performancePlanMonitoredDtoList = performancePlanMonitoredDtoList;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanName() {
        return this.planName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setPsystem(String psystem) {
        this.psystem = psystem;
    }

    public String getPsystem() {
        return this.psystem;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public Integer getScriptId() {
        return this.scriptId;
    }

    public void setScenarioId(Integer scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Integer getScenarioId() {
        return this.scenarioId;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanStatus() {
        return this.planStatus;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }

    @Override
    public String toString() {
        return "PerformancePlanShowDto{" +
                "id=" + id +
                ", planId=" + planId +
                ", planName='" + planName + '\'' +
                ", remark='" + remark + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", psystem='" + psystem + '\'' +
                ", scriptId=" + scriptId +
                ", scriptName='" + scriptName + '\'' +
                ", scenarioId=" + scenarioId +
                ", scenarioName='" + scenarioName + '\'' +
                ", planStatus='" + planStatus + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", stressMachineId=" + stressMachineId +
                ", stressMachineName='" + stressMachineName + '\'' +
                ", performancePlanMonitoredDtoList=" + performancePlanMonitoredDtoList +
                ", actualStartTime=" + actualStartTime +
                ", executeStatus='" + executeStatus + '\'' +
                ", setStartTime=" + setStartTime +
                ", resultPath='" + resultPath + '\'' +
                ", executePerson='" + executePerson + '\'' +
                '}';
    }
}

