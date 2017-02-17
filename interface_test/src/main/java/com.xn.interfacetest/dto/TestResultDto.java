/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * TestResult Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestResultDto extends BaseDto {
    
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
    private Long systemId;

    /**
     * 
     * 计划id 
     */
    private Long planId;

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

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public void setCaseIds(String caseIds) {
        this.caseIds = caseIds;
    }
    
    public String getCaseIds() {
        return this.caseIds;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }
    
    public Long getSystemId() {
        return this.systemId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    public Long getPlanId() {
        return this.planId;
    }

    public void setInterfaceIds(String interfaceIds) {
        this.interfaceIds = interfaceIds;
    }
    
    public String getInterfaceIds() {
        return this.interfaceIds;
    }

    public void setCaseCount(Long caseCount) {
        this.caseCount = caseCount;
    }
    
    public Long getCaseCount() {
        return this.caseCount;
    }

    public void setPassCaseCount(Long passCaseCount) {
        this.passCaseCount = passCaseCount;
    }
    
    public Long getPassCaseCount() {
        return this.passCaseCount;
    }

    public void setFailCaseCout(Long failCaseCout) {
        this.failCaseCout = failCaseCout;
    }
    
    public Long getFailCaseCout() {
        return this.failCaseCout;
    }

    public void setErrorCaseCout(Long errorCaseCout) {
        this.errorCaseCout = errorCaseCout;
    }
    
    public Long getErrorCaseCout() {
        return this.errorCaseCout;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public String getResult() {
        return this.result;
    }



}

