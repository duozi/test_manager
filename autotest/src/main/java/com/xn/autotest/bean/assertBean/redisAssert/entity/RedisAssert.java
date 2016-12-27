/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.redisAssert.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.cases.entity.Cases;

import java.util.Date;

/**
 * RedisAssert 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class RedisAssert extends BaseEntity {

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
     * 所属的计划id 
     */
    private Integer caseId;

    /**
     * 
     * redis校验是否可用，1表示可用，0表示不可用 
     */
    private Boolean redisAssertStatus;

    /**
     * 
     * 执行的redis名 
     */
    private String redisName;

    /**
     * 
     * 要校验的redis key 
     */
    private String redisKey;

    /**
     * 
     * redis校验内容，用json表示 
     */
    private String redisAssertContent;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;


    
    private Cases cases;
   
	
	public RedisAssert(){
	    // default constructor
	}
    
    public RedisAssert(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
    public Integer getCaseId() {
        return this.caseId;
    }
    
    public void setRedisAssertStatus(Boolean redisAssertStatus) {
        this.redisAssertStatus = redisAssertStatus;
    }
    
    public Boolean getRedisAssertStatus() {
        return this.redisAssertStatus;
    }
    
    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }
    
    public String getRedisName() {
        return this.redisName;
    }
    
    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
    
    public String getRedisKey() {
        return this.redisKey;
    }
    
    public void setRedisAssertContent(String redisAssertContent) {
        this.redisAssertContent = redisAssertContent;
    }
    
    public String getRedisAssertContent() {
        return this.redisAssertContent;
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
