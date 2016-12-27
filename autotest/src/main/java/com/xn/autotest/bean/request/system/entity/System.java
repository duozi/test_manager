/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.system.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.interfacesBean.entity.Interface;
import com.xn.autotest.bean.request.plan.entity.Plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * System 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class System extends BaseEntity {

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
     * 系统名称 
     */
    private String systemName;

    /**
     * 
     * 系统的类型,0表示http,1表示dubbo 
     */
    private Boolean type;

    /**
     * 
     * 系统是否可用,1表示可用,0表示不可以用 
     */
    private Boolean systemStatus;

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

    
    private List<Interface> interfaces = new ArrayList<Interface>();

    
    private Plan plan;
   
	
	public System(){
	    // default constructor
	}
    
    public System(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    
    public Integer getPlanId() {
        return this.planId;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public String getSystemName() {
        return this.systemName;
    }
    
    public void setType(Boolean type) {
        this.type = type;
    }
    
    public Boolean getType() {
        return this.type;
    }
    
    public void setSystemStatus(Boolean systemStatus) {
        this.systemStatus = systemStatus;
    }
    
    public Boolean getSystemStatus() {
        return this.systemStatus;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    
    public void setJarStatus(Boolean jarStatus) {
        this.jarStatus = jarStatus;
    }
    
    public Boolean getJarStatus() {
        return this.jarStatus;
    }
    

    
    public void setInterfaces(List<Interface> interfaces){
        this.interfaces = interfaces;
    }
    
    public List<Interface> getInterfaces() {
        return interfaces;
    }

    
    public void setPlan(Plan plan){
        this.plan = plan;
    }
    
    public Plan getPlan() {
        return plan;
    }
}
