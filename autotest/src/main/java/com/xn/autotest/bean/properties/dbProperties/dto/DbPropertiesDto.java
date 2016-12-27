/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.dbProperties.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * DbProperties Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class DbPropertiesDto extends BaseDto {
    
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
     * 数据库名称 
     */
    private String dbName;

    /**
     * 
     * 所属的计划id 
     */
    private Integer planeId;

    /**
     * 
     * 数据库配置是否可用，1表示可用，0表示不可用 
     */
    private Boolean dbStatus;

    /**
     * 
     * 连接数据库url 
     */
    private String url;

    /**
     * 
     * 连接数据库用户名 
     */
    private String userName;

    /**
     * 
     * 连接数据库密码 
     */
    private String pwd;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 数据库驱动 
     */
    private String dbDriver;

    
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

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String getDbName() {
        return this.dbName;
    }

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }
    
    public Integer getPlaneId() {
        return this.planeId;
    }

    public void setDbStatus(Boolean dbStatus) {
        this.dbStatus = dbStatus;
    }
    
    public Boolean getDbStatus() {
        return this.dbStatus;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return this.userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getPwd() {
        return this.pwd;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }
    
    public String getDbDriver() {
        return this.dbDriver;
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

