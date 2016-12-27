/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.method.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.cases.entity.Cases;
import com.xn.autotest.bean.request.interfacesBean.entity.Interface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Method 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class Method extends BaseEntity {

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
     * 对应的接口id 
     */
    private Integer interfaceId;

    /**
     * 
     * 方法名称 
     */
    private String methodName;

    /**
     * 
     * 方法是否可用,1表示可用,0表示不可以用 
     */
    private Boolean methodStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    private List<Cases> casess = new ArrayList<Cases>();

    
    private Interface interfaceBean;
   
	
	public Method(){
	    // default constructor
	}
    
    public Method(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Integer getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public void setMethodStatus(Boolean methodStatus) {
        this.methodStatus = methodStatus;
    }
    
    public Boolean getMethodStatus() {
        return this.methodStatus;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    

    
    public void setCasess(List<Cases> casess){
        this.casess = casess;
    }
    
    public List<Cases> getCasess() {
        return casess;
    }

    
    public void setInterface(Interface interfaceBean){
        this.interfaceBean = interfaceBean;
    }
    
    public Interface getInterface() {
        return interfaceBean;
    }
}
