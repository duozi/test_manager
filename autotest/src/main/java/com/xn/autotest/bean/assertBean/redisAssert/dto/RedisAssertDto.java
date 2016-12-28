/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.redisAssert.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * RedisAssert Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class RedisAssertDto extends BaseDto{
    
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

