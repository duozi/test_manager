/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.interfacetest.base.BaseDto;

/**
 * TestEnvironment Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestEnvironmentDto extends BaseDto {
    
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
     * 环境名称 
     */
    private String name;

    /**
     * 
     * 环境描述 
     */
    private String description;

    /**
     * 
     * 系统id 
     */
    private Long systemId;

    /**
     * 是否有service配置
     */
    private Integer serviceProperty;

    /**
     * 是否有db配置
     */
    private Integer dbProperty;

    /**
     * 是否有redis配置
     */
    private Integer redisProperty;

    /**
     * 系统
     */
    private  TestSystemDto systemDto;

    /**
     * 状态
     */
    private int status;

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

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSystemId() {
        return this.systemId;
    }
    
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public TestSystemDto getSystemDto() {
        return systemDto;
    }

    public void setSystemDto(TestSystemDto systemDto) {
        this.systemDto = systemDto;
    }

    public Integer getServiceProperty() {
        return serviceProperty;
    }

    public void setServiceProperty(Integer serviceProperty) {
        this.serviceProperty = serviceProperty;
    }

    public Integer getDbProperty() {
        return dbProperty;
    }

    public void setDbProperty(Integer dbProperty) {
        this.dbProperty = dbProperty;
    }

    public Integer getRedisProperty() {
        return redisProperty;
    }

    public void setRedisProperty(Integer redisProperty) {
        this.redisProperty = redisProperty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

