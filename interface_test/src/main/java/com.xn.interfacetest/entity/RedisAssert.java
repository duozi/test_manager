/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.entity.BaseEntity;

/**
 * RedisAssert 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RedisAssert extends BaseEntity {

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
    private Long redisConfigId;

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


    
    private TestRedisConfig testRedisConfig;
   
    
    private TestCase testCase;
   
	
	public RedisAssert(){
	    // default constructor
	}
    
    public RedisAssert(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setRedisConfigId(Long redisConfigId) {
        this.redisConfigId = redisConfigId;
    }
    
    public Long getRedisConfigId() {
        return this.redisConfigId;
    }
    
    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }
    
    public String getSqlStr() {
        return this.sqlStr;
    }
    
    public void setAssertParam(String assertParam) {
        this.assertParam = assertParam;
    }
    
    public String getAssertParam() {
        return this.assertParam;
    }
    
    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }
    
    public String getRightValue() {
        return this.rightValue;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }
    


    
    public void setTestRedisConfig(TestRedisConfig testRedisConfig){
        this.testRedisConfig = testRedisConfig;
    }
    
    public TestRedisConfig getTestRedisConfig() {
        return testRedisConfig;
    }
    
    public void setTestCase(TestCase testCase){
        this.testCase = testCase;
    }
    
    public TestCase getTestCase() {
        return testCase;
    }
}
