/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * TestDatabaseConfig 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestDatabaseConfig extends BaseEntity {

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
     * 数据库配置名 
     */
    private String name;

    /**
     * 
     * 数据库类型（mysql，oracle） 
     */
    private Integer type;

    /**
     * 
     * Ip地址 
     */
    private String ipAddress;

    /**
     * 
     * 端口号 
     */
    private Integer portAddress;

    /**
     * 
     * 数据库名 
     */
    private String databaseName;

    /**
     * 
     * 登录用户名 
     */
    private String userName;

    /**
     * 
     * 登录密码 
     */
    private String password;

    /**
     * 
     * 环境id 
     */
    private Long environmentId;

    
    private List<DataAssert> dataAsserts = new ArrayList<DataAssert>();

	
	public TestDatabaseConfig(){
	    // default constructor
	}
    
    public TestDatabaseConfig(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setPortAddress(Integer portAddress) {
        this.portAddress = portAddress;
    }
    
    public Integer getPortAddress() {
        return this.portAddress;
    }
    
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    public String getDatabaseName() {
        return this.databaseName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }
    
    public Long getEnvironmentId() {
        return this.environmentId;
    }
    

    
    public void setDataAsserts(List<DataAssert> dataAsserts){
        this.dataAsserts = dataAsserts;
    }
    
    public List<DataAssert> getDataAsserts() {
        return dataAsserts;
    }

}
