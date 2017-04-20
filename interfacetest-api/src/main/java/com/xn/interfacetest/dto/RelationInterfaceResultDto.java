/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.interfacetest.base.BaseDto;
import com.xn.interfacetest.response.AssertItem;

import java.util.List;

/**
 * RelationInterfaceResult Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationInterfaceResultDto extends BaseDto {
    
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
     * suitId 
     */
    private Long suitId;

    /**
     * 
     * interfaceId 
     */
    private Long interfaceId;

    /**
     * 
     * caseId 
     */
    private Long caseId;

    /**
     *
     * suitId
     */
    private String suitName;

    /**
     *
     * interfaceId
     */
    private String interfaceName;

    /**
     *
     * caseId
     */
    private String caseName;

    /**
     * 
     * planId 
     */
    private Long planId;

    /**
     * 
     * reportId 
     */
    private Long reportId;

    /**
     * 
     * requestData 
     */
    private String requestData;

    /**
     * 
     * responseData 
     */
    private String responseData;

    /**
     * 占用时间
     */
    private Long costTime;

    /**
     * 执行时间
     */
    private String excuteTime;

    /**
     * 
     * 请求结果(通过，错误，失败) 
     */
    private String result;

    /**
     * 断言结果，用来展示期望值和实际值
     */
    private List<AssertItem> assertItemList;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuitId() {
        return this.suitId;
    }
    
    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }

    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getReportId() {
        return this.reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getRequestData() {
        return this.requestData;
    }
    
    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return this.responseData;
    }
    
    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getSuitName() {
        return suitName;
    }

    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public List<AssertItem> getAssertItemList() {
        return assertItemList;
    }

    public void setAssertItemList(List<AssertItem> assertItemList) {
        this.assertItemList = assertItemList;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }
}

