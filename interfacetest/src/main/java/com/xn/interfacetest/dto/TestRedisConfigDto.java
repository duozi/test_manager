/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;

import com.xn.common.base.BaseDto;

/**
 * TestRedisConfig Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestRedisConfigDto extends BaseDto {
    
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
     * redis名 
     */
    private String name;

    /**
     * 
     * timeout 
     */
    private Integer timeout;

    /**
     * 
     * 重试次数 
     */
    private Integer tryTime;

    /**
     * 
     * 集群 
     */
    private String ipAddress;

    /**
     * 
     * 环境id 
     */
    private Long environmentId;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeout() {
        return this.timeout;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getTryTime() {
        return this.tryTime;
    }
    
    public void setTryTime(Integer tryTime) {
        this.tryTime = tryTime;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getEnvironmentId() {
        return this.environmentId;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }



}

