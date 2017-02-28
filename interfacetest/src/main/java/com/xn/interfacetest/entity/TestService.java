/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * TestService 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestService extends BaseEntity {

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
     * 服务名称 
     */
    private String name;

    /**
     * 
     * Varchar(200) 
     */
    private String description;

    /**
     * 
     * 系统id 
     */
    private Long systemId;

    /**
     * jar包路径
     */
    private String jarPath;
	
	public TestService(){
	    // default constructor
	}
    
    public TestService(Long id){
        this.id = id;
    }

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

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
