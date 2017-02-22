/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.entity;

import com.xn.performance.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * PerformanceScript 实体类
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceScript extends BaseEntity {

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
     * 脚本名 
     */
    private String scriptName;

    /**
     * 
     * 脚本描述 
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
     * 脚本状态，0，未发布，1，已发布 
     */
    private String scriptStatus;

    /**
     * 
     * 存放路径 
     */
    private String path;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    
    private List<PerformancePlan> performancePlans = new ArrayList<PerformancePlan>();

	
	public PerformanceScript(){
	    // default constructor
	}
    
    public PerformanceScript(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
    
    public String getScriptName() {
        return this.scriptName;
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
    
    public void setScriptStatus(String scriptStatus) {
        this.scriptStatus = scriptStatus;
    }
    
    public String getScriptStatus() {
        return this.scriptStatus;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    public String getCreatePerson() {
        return this.createPerson;
    }
    

    
    public void setPerformancePlans(List<PerformancePlan> performancePlans){
        this.performancePlans = performancePlans;
    }
    
    public List<PerformancePlan> getPerformancePlans() {
        return performancePlans;
    }

}
