/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dto;


import com.xn.performance.mybatis.BaseDto;

/**
 * PerformanceStressMachine Dto 对象
 *
 * @author zhouxi
 * @date 2017-02-21
 */
public class PerformanceStressMachineDto extends BaseDto {

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
     * 压力机名
     */
    private String stressMachineName;

    /**
     * 备注
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
     * ip
     */
    private String ip;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建人
     */
    private String createPerson;
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    private String stressMachineStatus;

    public PerformanceStressMachineDto(Integer id) {
        this.id = id;
    }

    public PerformanceStressMachineDto() {

    }

    public String getStressMachineStatus() {
        return stressMachineStatus;
    }

    public void setStressMachineStatus(String stressMachineStatus) {
        this.stressMachineStatus = stressMachineStatus;
    }


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

