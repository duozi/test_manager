/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.base.BaseEntity;

/**
 * DataAssert 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class DataAssert extends BaseEntity {

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
     * 数据库id 
     */
    private Long databaseConfigId;

    /**
     * 
     * Sql 
     */
    private String sqlStr;

    /**
     * 
     * 校验字段 
     */
    private String assertParam;

    /**
     * 
     * 期望值 
     */
    private String rightValue;

    /**
     * 
     * 用例id 
     */
    private Long caseId;


//
//    private TestCase testCase;
//
//
//    private TestDatabaseConfig testDatabaseConfig;
//
	
	public DataAssert(){
	    // default constructor
	}
    
    public DataAssert(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getDatabaseConfigId() {
        return this.databaseConfigId;
    }
    
    public void setDatabaseConfigId(Long databaseConfigId) {
        this.databaseConfigId = databaseConfigId;
    }
    
    public String getSqlStr() {
        return this.sqlStr;
    }
    
    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }
    
    public String getAssertParam() {
        return this.assertParam;
    }
    
    public void setAssertParam(String assertParam) {
        this.assertParam = assertParam;
    }
    
    public String getRightValue() {
        return this.rightValue;
    }
    
    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    


    
//    public void setTestCase(TestCase testCase){
//        this.testCase = testCase;
//    }
//
//    public TestCase getTestCase() {
//        return testCase;
//    }
//
//    public void setTestDatabaseConfig(TestDatabaseConfig testDatabaseConfig){
//        this.testDatabaseConfig = testDatabaseConfig;
//    }
//
//    public TestDatabaseConfig getTestDatabaseConfig() {
//        return testDatabaseConfig;
//    }
}
