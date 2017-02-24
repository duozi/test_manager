/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * TestDatabaseConfig Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestDatabaseConfigDto extends BaseDto {
    
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

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPortAddress() {
        return this.portAddress;
    }
    
    public void setPortAddress(Integer portAddress) {
        this.portAddress = portAddress;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
    
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEnvironmentId() {
        return this.environmentId;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }



}

