/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;




/**
 * TestService Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestServiceDto extends BaseDto {
    
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
     * 服务名称 
     */

    private String name;

    /**
     * 
     * Varchar(200) 
     */
    private String description;

    /**
     * 
     * 系统id 
     */
    private Long systemId;

    /*
     * 系统(包含公司信息)
    */
    private TestSystemDto systemDto;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSystemId() {
        return this.systemId;
    }
    
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public TestSystemDto getSystemDto() {
        return systemDto;
    }

    public void setSystemDto(TestSystemDto systemDto) {
        this.systemDto = systemDto;
    }
}

