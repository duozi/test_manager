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
    private String stressMachineStatus;

    public PerformanceStressMachineDto(Integer id) {
        this.id = id;
    }


    public PerformanceStressMachineDto() {

    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStressMachineStatus() {
        return stressMachineStatus;
    }

    public void setStressMachineStatus(String stressMachineStatus) {
        this.stressMachineStatus = stressMachineStatus;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStressMachineName() {
        return this.stressMachineName;
    }

    public void setStressMachineName(String stressMachineName) {
        this.stressMachineName = stressMachineName;
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

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }


}

