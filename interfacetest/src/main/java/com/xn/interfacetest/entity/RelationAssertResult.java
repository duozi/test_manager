/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.base.BaseEntity;

/**
 * RelationAssertResult 实体类
 * 
 * @author Carol
 * @date 2017-03-31
 */
public class RelationAssertResult extends BaseEntity {

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
     * reportId 
     */
    private Long reportId;


    private Long caseId;

    private Long interfaceId;

    /**
     * 
     * 参数断言的id 
     */
    private Long paramsAssertId;

    /**
     * 
     * 数据库断言的id 
     */
    private Long dbAssertId;

    /**
     * 
     * redis断言的id 
     */
    private Long redisAssertId;

    /**
     * 断言字段
     */
    private String assertKey;

    /**
     * 期望值
     */
    private String expectValue;

    /**
     * 实际值
     */
    private String exactValue;

    /**
     * 
     * 断言判断的结果 
     */
    private String result;


	
	public RelationAssertResult(){
	    // default constructor
	}
    
    public RelationAssertResult(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getReportId() {
        return this.reportId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    public Long getParamsAssertId() {
        return this.paramsAssertId;
    }
    
    public void setParamsAssertId(Long paramsAssertId) {
        this.paramsAssertId = paramsAssertId;
    }
    
    public Long getDbAssertId() {
        return this.dbAssertId;
    }
    
    public void setDbAssertId(Long dbAssertId) {
        this.dbAssertId = dbAssertId;
    }
    
    public Long getRedisAssertId() {
        return this.redisAssertId;
    }
    
    public void setRedisAssertId(Long redisAssertId) {
        this.redisAssertId = redisAssertId;
    }
    
    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public String getAssertKey() {
        return assertKey;
    }

    public void setAssertKey(String assertKey) {
        this.assertKey = assertKey;
    }

    public String getExpectValue() {
        return expectValue;
    }

    public void setExpectValue(String expectValue) {
        this.expectValue = expectValue;
    }

    public String getExactValue() {
        return exactValue;
    }

    public void setExactValue(String exactValue) {
        this.exactValue = exactValue;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
}
