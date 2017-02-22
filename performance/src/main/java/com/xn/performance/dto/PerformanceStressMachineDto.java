/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;

import com.xn.performance.annotation.NotNullField;
import com.xn.performance.util.BaseDto;


/**
 * PerformanceStressMachine Dto 对象
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceStressMachineDto extends BaseDto{
    
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
     * 压力机名 
     */
    @NotNullField
    private String stressMachineName;

    /**
     * 
     * 备注 
     */
    private String remark;

    /**
     * 
     * 公司 
     */
    @NotNullField
    private String company;

    /**
     * 
     * 部门 
     */
    @NotNullField
    private String department;

    /**
     * 
     * 系统 
     */
    @NotNullField
    private String psystem;

    /**
     * 
     * ip 
     */
    @NotNullField
    private String ip;

    /**
     * 
     * 用户名 
     */
    @NotNullField
    private String username;

    /**
     * 
     * 密码 
     */
    @NotNullField
    private String password;

    /**
     * 
     * 创建人 
     */
    @NotNullField
    private String createPerson;

    

    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setStressMachineName(String stressMachineName) {
        this.stressMachineName = stressMachineName;
    }
    
    public String getStressMachineName() {
        return this.stressMachineName;
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



}

