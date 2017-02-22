/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * RelationDatabaseEnvironment 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationDatabaseEnvironment extends BaseEntity {

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


	
	public RelationDatabaseEnvironment(){
	    // default constructor
	}
    
    public RelationDatabaseEnvironment(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getDatabaseId() {
        return this.databaseId;
    }
    
    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
    
    public Long getEnvironmentId() {
        return this.environmentId;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }
    
    public Long getServiceId() {
        return this.serviceId;
    }
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    


}
