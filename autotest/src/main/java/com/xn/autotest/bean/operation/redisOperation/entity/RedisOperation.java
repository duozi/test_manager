/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.redisOperation.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.cases.entity.Cases;
import com.xn.autotest.enums.RedisOperationEnum;

import java.util.Date;

/**
 * RedisOperation 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class RedisOperation extends BaseEntity {

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
     * redis名称 
     */
    private String redisName;

    /**
     * 
     * 所属的计划id 
     */
    private Integer caseId;

    /**
     * 
     * redis操作是否可用，1表示可用，0表示不可用 
     */
    private Boolean redisStatus;

    /**
     * 
     * redis操作类型，1 beforeclass 2 afterclass 3 before ,4 after
     */
    private RedisOperationEnum type;

    /**
     * 
     * 执行顺序，从1开始 
     */
    private Integer operationOrder;

    /**
     * 
     * 执行redis key 
     */
    private String redisKey;

    /**
     * 
     * 执行redis value 
     */
    private String redisValue;

    /**
     * 
     * redisProperties 缓存时间
     */
    private String redisTime;

    /**
     * 
     * 执行redis 操作类型，1 set，2 get 3del  4 settime
     */
    private RedisOperationEnum actionType;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;


    
    private Cases cases;
   
	
	public RedisOperation(){
	    // default constructor
	}
    
    public RedisOperation(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }
    
    public String getRedisName() {
        return this.redisName;
    }
    
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
    public Integer getCaseId() {
        return this.caseId;
    }
    
    public void setRedisStatus(Boolean redisStatus) {
        this.redisStatus = redisStatus;
    }
    
    public Boolean getRedisStatus() {
        return this.redisStatus;
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
    
    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
    
    public String getRedisKey() {
        return this.redisKey;
    }
    
    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }
    
    public String getRedisValue() {
        return this.redisValue;
    }
    
    public void setRedisTime(String redisTime) {
        this.redisTime = redisTime;
    }
    
    public String getRedisTime() {
        return this.redisTime;
    }
    
    public void setActionType(RedisOperationEnum actionType) {
        this.actionType = actionType;
    }
    
    public RedisOperationEnum getActionType() {
        return this.actionType;
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
