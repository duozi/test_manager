/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import java.util.ArrayList;
import java.util.List;

import com.xn.common.base.BaseEntity;

/**
 * TestEnvironment 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestEnvironment extends BaseEntity {

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

    
    private List<TestRedisConfig> testRedisConfigs = new ArrayList<TestRedisConfig>();

	
	public TestEnvironment(){
	    // default constructor
	}
    
    public TestEnvironment(Long id){
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
    
    public List<TestRedisConfig> getTestRedisConfigs() {
        return testRedisConfigs;
    }
    
    public void setTestRedisConfigs(List<TestRedisConfig> testRedisConfigs){
        this.testRedisConfigs = testRedisConfigs;
    }

}
