/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

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
     * 
     * 请求结果(通过，错误，失败) 
     */
    private Integer result;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }
    
    public Long getSuitId() {
        return this.suitId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    public Long getPlanId() {
        return this.planId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    public Long getReportId() {
        return this.reportId;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }
    
    public String getRequestData() {
        return this.requestData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
    
    public String getResponseData() {
        return this.responseData;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
    
    public Integer getResult() {
        return this.result;
    }



}

