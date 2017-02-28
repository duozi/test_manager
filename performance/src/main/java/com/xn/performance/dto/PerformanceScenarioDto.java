/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;

import com.xn.performance.util.BaseDto;


/**
 * PerformanceScenario Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceScenarioDto extends BaseDto{
    
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
     * 场景名称 
     */
    private String scenarioName;

    /**
     * 
     * 场景描述 
     */
    private String remark;

    /**
     * 
     * 系统 
     */
    private String psystem;

    /**
     * 
     * 部门 
     */
    private String department;

    /**
     * 
     * 公司 
     */
    private String company;

    /**
     * 
     * 并发数 
     */
    private Integer concurrency;

    /**
     * 
     * 所有线程启动完毕时间 
     */
    private Integer startup;

    /**
     * 
     * 执行时间 
     */
    private Integer executeTime;

    /**
     * 
     * 循环次数 
     */
    private Integer cycle;

    /**
     * 
     * 场景状态，0，未发布，1，已发布 
     */
    private String scenarioStatus;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getScenarioName() {
        return this.scenarioName;
    }
    
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPsystem() {
        return this.psystem;
    }
    
    public void setPsystem(String psystem) {
        this.psystem = psystem;
    }

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getConcurrency() {
        return this.concurrency;
    }
    
    public void setConcurrency(Integer concurrency) {
        this.concurrency = concurrency;
    }

    public Integer getStartup() {
        return this.startup;
    }
    
    public void setStartup(Integer startup) {
        this.startup = startup;
    }

    public Integer getExecuteTime() {
        return this.executeTime;
    }
    
    public void setExecuteTime(Integer executeTime) {
        this.executeTime = executeTime;
    }

    public Integer getCycle() {
        return this.cycle;
    }
    
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getScenarioStatus() {
        return this.scenarioStatus;
    }
    
    public void setScenarioStatus(String scenarioStatus) {
        this.scenarioStatus = scenarioStatus;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }



}

