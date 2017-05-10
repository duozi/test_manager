/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.mybatis.BaseDto;

/**
 * PerformanceScript Dto 对象
 *
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceScriptDto extends BaseDto {

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
     * 脚本名
     */
    private String scriptName;

    /**
     * 脚本描述
     */
    private String remark;

    /**
     * 公司
     */
    private String company;

    /**
     * 部门
     */
    private String department;

    /**
     * 系统
     */
    private String psystem;

    /**
     * 脚本状态，0，未发布，1，已发布
     */
    private String scriptStatus;

    /**
     * 脚本名称
     */
    private String scriptFileName;

    /**
     * 创建人
     */
    private String createPerson;



    private String dependenceFileName;

    public String getDependenceFileName() {
        return dependenceFileName;
    }

    public void setDependenceFileName(String dependenceFileName) {
        this.dependenceFileName = dependenceFileName;
    }
    public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScriptName() {
        return this.scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPsystem() {
        return this.psystem;
    }

    public void setPsystem(String psystem) {
        this.psystem = psystem;
    }

    public String getScriptStatus() {
        return this.scriptStatus;
    }

    public void setScriptStatus(String scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }


}

