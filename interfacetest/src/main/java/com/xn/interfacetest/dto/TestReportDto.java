/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;
import com.xn.interfacetest.entity.TestPlan;

import java.util.List;

/**
 * TestReport Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestReportDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * id 
     */
    private Long id;

    /**
     * 
     * 测试报告名称 
     */
    private String name;

    /**
     * 
     * 用例id，逗号隔开 
     */
    private String caseIds;

    /**
     * 
     * 系统id 
     */
    private String systemIds;

    /**
     *
     * 计划id
     */
    private Long planId;

    /**
     *
     * 环境id
     */
    private Long environmentId;

    /**
     * 
     * 接口id，逗号隔开 
     */
    private String interfaceIds;

    /**
     * 
     * 执行用例数 
     */
    private Long caseCount;

    /**
     * 
     * 用例通过数 
     */
    private Long passCaseCount;

    /**
     * 
     * 失败用例数 
     */
    private Long failCaseCout;

    /**
     * 
     * 错误用例数 
     */
    private Long errorCaseCout;

    /**
     * 
     * 测试结果 
     */
    private String result;

    /**
     * 计划开始执行的时间
     */
    private String beginTime;

    /**
     * 计划执行结束的时间
     */
    private String endTime;

    /**
     * 计划执行消耗的时间
     */
    private Integer costTime;

    private TestPlanDto plan;

    private TestEnvironmentDto environment;

    private List<TestSuitDto> suitList;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCaseIds() {
        return this.caseIds;
    }
    
    public void setCaseIds(String caseIds) {
        this.caseIds = caseIds;
    }

    public String getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(String systemIds) {
        this.systemIds = systemIds;
    }

    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getInterfaceIds() {
        return this.interfaceIds;
    }
    
    public void setInterfaceIds(String interfaceIds) {
        this.interfaceIds = interfaceIds;
    }

    public Long getCaseCount() {
        return this.caseCount;
    }
    
    public void setCaseCount(Long caseCount) {
        this.caseCount = caseCount;
    }

    public Long getPassCaseCount() {
        return this.passCaseCount;
    }
    
    public void setPassCaseCount(Long passCaseCount) {
        this.passCaseCount = passCaseCount;
    }

    public Long getFailCaseCout() {
        return this.failCaseCout;
    }
    
    public void setFailCaseCout(Long failCaseCout) {
        this.failCaseCout = failCaseCout;
    }

    public Long getErrorCaseCout() {
        return this.errorCaseCout;
    }
    
    public void setErrorCaseCout(Long errorCaseCout) {
        this.errorCaseCout = errorCaseCout;
    }

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public TestPlanDto getPlan() {
        return plan;
    }

    public void setPlan(TestPlanDto plan) {
        this.plan = plan;
    }

    public TestEnvironmentDto getEnvironment() {
        return environment;
    }

    public void setEnvironment(TestEnvironmentDto environment) {
        this.environment = environment;
    }

    public List<TestSuitDto> getSuitList() {
        return suitList;
    }

    public void setSuitList(List<TestSuitDto> suitList) {
        this.suitList = suitList;
    }
}

