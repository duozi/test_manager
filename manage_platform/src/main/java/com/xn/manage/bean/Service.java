/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.manage.bean;

import java.util.Date;

/**
 * Service 实体类
 * 
 * @author Carol
 * @date 2017-01-22
 */
public class Service extends BaseEntity {

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
     * 对应的计划id 
     */
    private Integer planId;

    /**
     * 
     * 服务名称
     */
    private String serviceName;

    /**
     * 
     * 系统的类型,0表示http,1表示dubbo 
     */
    private Boolean type;

    /**
     * 
     * 服务是否可用,1表示可用,0表示不可以用
     */
    private Boolean serviceStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 是否上传jar包，1表示上传，0表示没有上传 
     */
    private Boolean jarStatus;

    

	public Service(){
	    // default constructor
	}
    
    public Service(Integer id){
        this.id = id;
    }

    public Service(Integer id,String serviceName){
        this.id = id;
        this.serviceName = serviceName;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getType() {
        return this.type;
    }
    
    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Boolean serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Boolean getJarStatus() {
        return this.jarStatus;
    }
    
    public void setJarStatus(Boolean jarStatus) {
        this.jarStatus = jarStatus;
    }

}
