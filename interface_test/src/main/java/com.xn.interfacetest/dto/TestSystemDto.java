/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * TestSystem Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestSystemDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * id 
     */
    private Long id;

    /**
     * 
     * 系统名称 
     */
    private String name;

    /**
     * 
     * 系统描述 
     */
    private String description;

    /**
     * 
     * 部门id 
     */
    private Long departmentId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    public Long getDepartmentId() {
        return this.departmentId;
    }



}

