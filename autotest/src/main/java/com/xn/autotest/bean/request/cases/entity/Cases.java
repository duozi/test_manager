/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.cases.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.assertBean.dbAssert.entity.DbAssert;
import com.xn.autotest.bean.assertBean.parameterAssert.entity.ParameterAssert;
import com.xn.autotest.bean.assertBean.redisAssert.entity.RedisAssert;
import com.xn.autotest.bean.operation.dbOperation.entity.DbOperation;
import com.xn.autotest.bean.operation.redisOperation.entity.RedisOperation;
import com.xn.autotest.bean.request.method.entity.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cases 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class Cases extends BaseEntity {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的方法id 
     */
    private Integer methodId;

    /**
     * 
     * case名称 
     */
    private String caseName;

    /**
     * 
     * case是否可用,1表示可用,0表示不可以用 
     */
    private Boolean caseStatus;

    /**
     * 
     * case的参数，用json表示 
     */
    private String param;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    private List<DbOperation> dbOperations = new ArrayList<DbOperation>();
    
    private List<RedisAssert> redisAsserts = new ArrayList<RedisAssert>();
    
    private List<RedisOperation> redisOperations = new ArrayList<RedisOperation>();
    
    private List<DbAssert> dbAsserts = new ArrayList<DbAssert>();
    
    private List<ParameterAssert> parameterAsserts = new ArrayList<ParameterAssert>();

    
    private Method method;
   
	
	public Cases(){
	    // default constructor
	}
    
    public Cases(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }
    
    public Integer getMethodId() {
        return this.methodId;
    }
    
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    
    public String getCaseName() {
        return this.caseName;
    }
    
    public void setCaseStatus(Boolean caseStatus) {
        this.caseStatus = caseStatus;
    }
    
    public Boolean getCaseStatus() {
        return this.caseStatus;
    }
    
    public void setParam(String param) {
        this.param = param;
    }
    
    public String getParam() {
        return this.param;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    

    
    public void setDbOperations(List<DbOperation> dbOperations){
        this.dbOperations = dbOperations;
    }
    
    public List<DbOperation> getDbOperations() {
        return dbOperations;
    }
    
    public void setRedisAsserts(List<RedisAssert> redisAsserts){
        this.redisAsserts = redisAsserts;
    }
    
    public List<RedisAssert> getRedisAsserts() {
        return redisAsserts;
    }
    
    public void setRedisOperations(List<RedisOperation> redisOperations){
        this.redisOperations = redisOperations;
    }
    
    public List<RedisOperation> getRedisOperations() {
        return redisOperations;
    }
    
    public void setDbAsserts(List<DbAssert> dbAsserts){
        this.dbAsserts = dbAsserts;
    }
    
    public List<DbAssert> getDbAsserts() {
        return dbAsserts;
    }
    
    public void setParameterAsserts(List<ParameterAssert> parameterAsserts){
        this.parameterAsserts = parameterAsserts;
    }
    
    public List<ParameterAssert> getParameterAsserts() {
        return parameterAsserts;
    }

    
    public void setMethod(Method method){
        this.method = method;
    }
    
    public Method getMethod() {
        return method;
    }
}
