/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.redisProperties.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * RedisProperties Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class RedisPropertiesDto extends BaseDto{
    
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
    private Integer planeId;

    /**
     * 
     * 数据库配置是否可用，1表示可用，0表示不可用 
     */
    private Boolean redisStatus;

    /**
     * 
     * 连redis的host和port，用host:port表示,多个用，隔开 
     */
    private String redisHostPort;

    /**
     * 
     * 连接redis超时设置 
     */
    private Integer timeout;

    /**
     * 
     * redis重定向次数 
     */
    private Integer redirections;

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

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }
    
    public Integer getPlaneId() {
        return this.planeId;
    }

    public void setRedisStatus(Boolean redisStatus) {
        this.redisStatus = redisStatus;
    }
    
    public Boolean getRedisStatus() {
        return this.redisStatus;
    }

    public void setRedisHostPort(String redisHostPort) {
        this.redisHostPort = redisHostPort;
    }
    
    public String getRedisHostPort() {
        return this.redisHostPort;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public Integer getTimeout() {
        return this.timeout;
    }

    public void setRedirections(Integer redirections) {
        this.redirections = redirections;
    }
    
    public Integer getRedirections() {
        return this.redirections;
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

