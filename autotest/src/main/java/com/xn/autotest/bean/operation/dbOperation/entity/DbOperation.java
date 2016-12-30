/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.dbOperation.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.cases.entity.Cases;
import com.xn.autotest.enums.RedisOperationEnum;

import java.util.Date;

/**
 * DbOperation 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class DbOperation extends BaseEntity {

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
     * 数据库名称 
     */
    private String dbName;

    /**
     * 
     * 所属的计划id 
     */
    private Integer caseId;

    /**
     * 
     * 数据库操作是否可用，1表示可用，0表示不可用 
     */
    private Boolean dbStatus;

    /**
     * 
     * 数据库操作类型，1 beforeclass 2 afterclass 3 before ,4 after 
     */
    private RedisOperationEnum type;

    /**
     * 
     * 执行顺序 从1开始 
     */
    private Integer operationOrder;

    /**
     * 
     * 执行数据库sql语句 
     */
    private String sqlString;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;


    
    private Cases cases;
   
	
	public DbOperation(){
	    // default constructor
	}
    
    public DbOperation(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String getDbName() {
        return this.dbName;
    }
    
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
    public Integer getCaseId() {
        return this.caseId;
    }
    
    public void setDbStatus(Boolean dbStatus) {
        this.dbStatus = dbStatus;
    }
    
    public Boolean getDbStatus() {
        return this.dbStatus;
    }
    
    public void setType(RedisOperationEnum type) {
        this.type = type;
    }
    
    public RedisOperationEnum getType() {
        return this.type;
    }
    
    public void setOperationOrder(Integer operationOrder) {
        this.operationOrder = operationOrder;
    }
    
    public Integer getOperationOrder() {
        return this.operationOrder;
    }
    
    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }
    
    public String getSqlString() {
        return this.sqlString;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    


    
    public void setCases(Cases cases){
        this.cases = cases;
    }
    
    public Cases getCases() {
        return cases;
    }
}
