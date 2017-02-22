/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.entity;

import com.xn.performance.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * PerformancePlan 实体类
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformancePlan extends BaseEntity {

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
     * 场景id 
     */
    private Integer scenarioId;

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

    
    private List<PerformanceResult> performanceResults = new ArrayList<PerformanceResult>();

    
    private PerformanceScenario performanceScenario;
   
    
    private PerformanceScript performanceScript;
   
	
	public PerformancePlan(){
	    // default constructor
	}
    
    public PerformancePlan(Integer id){
        this.id = id;
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
    

    
    public void setPerformanceResults(List<PerformanceResult> performanceResults){
        this.performanceResults = performanceResults;
    }
    
    public List<PerformanceResult> getPerformanceResults() {
        return performanceResults;
    }

    
    public void setPerformanceScenario(PerformanceScenario performanceScenario){
        this.performanceScenario = performanceScenario;
    }
    
    public PerformanceScenario getPerformanceScenario() {
        return performanceScenario;
    }
    
    public void setPerformanceScript(PerformanceScript performanceScript){
        this.performanceScript = performanceScript;
    }
    
    public PerformanceScript getPerformanceScript() {
        return performanceScript;
    }
}
