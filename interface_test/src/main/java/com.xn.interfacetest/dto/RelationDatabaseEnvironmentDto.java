/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;

import com.xn.common.entity.BaseDto;

/**
 * RelationDatabaseEnvironment Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationDatabaseEnvironmentDto extends BaseDto {
    
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
     * databaseId 
     */
    private Long databaseId;

    /**
     * 
     * environmentId 
     */
    private Long environmentId;

    /**
     * 
     * serviceId 
     */
    private Long serviceId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
    
    public Long getDatabaseId() {
        return this.databaseId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }
    
    public Long getEnvironmentId() {
        return this.environmentId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public Long getServiceId() {
        return this.serviceId;
    }



}

