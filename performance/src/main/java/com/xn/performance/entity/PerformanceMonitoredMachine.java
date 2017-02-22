/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.entity;


import com.xn.performance.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * PerformanceMonitoredMachine 实体类
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceMonitoredMachine extends BaseEntity {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键列
     * id 
     */
    private Integer id;

    /**
     * 
     * 监控机名 
     */
    private String monitoredMachineName;

    /**
     * 
     * 备注 
     */
    private String remark;

    /**
     * 
     * 公司 
     */
    private String company;

    /**
     * 
     * 部门 
     */
    private String department;

    /**
     * 
     * 系统 
     */
    private String psystem;

    /**
     * 
     * ip 
     */
    private String ip;

    /**
     * 
     * 用户名 
     */
    private String username;

    /**
     * 
     * 密码 
     */
    private String password;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    
    private List<PerformanceResult> performanceResults = new ArrayList<PerformanceResult>();

	
	public PerformanceMonitoredMachine(){
	    // default constructor
	}
    
    public PerformanceMonitoredMachine(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setMonitoredMachineName(String monitoredMachineName) {
        this.monitoredMachineName = monitoredMachineName;
    }
    
    public String getMonitoredMachineName() {
        return this.monitoredMachineName;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getCompany() {
        return this.company;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getDepartment() {
        return this.department;
    }
    
    public void setPsystem(String psystem) {
        this.psystem = psystem;
    }
    
    public String getPsystem() {
        return this.psystem;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    public String getCreatePerson() {
        return this.createPerson;
    }
    

    
    public void setPerformanceResults(List<PerformanceResult> performanceResults){
        this.performanceResults = performanceResults;
    }
    
    public List<PerformanceResult> getPerformanceResults() {
        return performanceResults;
    }

}
