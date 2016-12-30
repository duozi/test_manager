/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.redisOperation.dto;

import com.xn.autotest.bean.BaseDto;
import com.xn.autotest.enums.RedisOperationEnum;

import java.util.Date;


/**
 * RedisOperation Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class RedisOperationDto extends BaseDto{
    
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
    private Integer redisTime;

    /**
     * 
     * 执行redis 操作类型，1 set，2 del 3 settime 
     */
    private RedisOperationEnum actionType;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    /**
     * changeTime
     */
    private Date changeTimeBegin;

    /**
     * changeTime
     */
    private Date changeTimeEnd;


    
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

    public void setRedisTime(Integer redisTime) {
        this.redisTime = redisTime;
    }
    
    public Integer getRedisTime() {
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


    public Date getChangeTimeBegin() {
        return this.changeTimeBegin;
    }
    
    public void setChangeTimeBegin(Date changeTimeBegin) {
        this.changeTimeBegin = changeTimeBegin;
    }    
    
    public Date getChangeTimeEnd() {
        return this.changeTimeEnd;
    }
    
    public void setChangeTimeEnd(Date changeTimeEnd) {
        this.changeTimeEnd = changeTimeEnd;
    }
    

}

