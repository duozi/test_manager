/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;

import com.xn.performance.util.BaseDto;


/**
 * PerformancePlan Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformancePlanDto extends BaseDto{
    
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
     * 计划名 
     */
    private String planName;

    /**
     * 
     * 计划描述 
     */
    private String remark;

    /**
     * 
     * 公司 
     */
    private String company;

    /**
     * 
     * 部门 
     */
    private String department;

    /**
     * 
     * 系统 
     */
    private String psystem;

    /**
     * 
     * 脚本id 
     */
    private Integer scriptId;
    /**
     *
     * 脚本名称
     */
    private String scriptName;
    /**
     * 
     * 场景id 
     */
    private Integer scenarioId;
    /**
     *
     * 场景名称
     */
    private String scenarioName;

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

    /**
     * 
     * 计划状态

     */
    private String planStatus;

    /**
     * 
     * 创建人 
     */
    private String createPerson;




    /**
     * 是否已删除
     */

    private String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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



}

