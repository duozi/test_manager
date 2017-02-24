/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * TestEnvironment Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestEnvironmentDto extends BaseDto {
    
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
     * 环境名称 
     */
    private String name;

    /**
     * 
     * 环境描述 
     */
    private String description;

    /**
     * 
     * 系统id 
     */
    private Long systemId;

    

    
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

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }
    
    public Long getSystemId() {
        return this.systemId;
    }



}

