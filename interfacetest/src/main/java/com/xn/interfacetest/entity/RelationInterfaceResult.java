/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * RelationInterfaceResult 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationInterfaceResult extends BaseEntity {

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


	
	public RelationInterfaceResult(){
	    // default constructor
	}
    
    public RelationInterfaceResult(Long id){
        this.id = id;
    }

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
    
    public Integer getResult() {
        return this.result;
    }
    
    public void setResult(Integer result) {
        this.result = result;
    }
    


}
