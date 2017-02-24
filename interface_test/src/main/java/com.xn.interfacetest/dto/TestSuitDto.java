/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;

import com.xn.common.entity.BaseDto;

/**
 * TestSuit Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestSuitDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * 自增长id 
     */
    private Long id;

    /**
     * 
     * 测试集名称 
     */
    private String name;

    /**
     * 
     * 测试集描述 
     */
    private String description;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    

    
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

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    public String getCreatePerson() {
        return this.createPerson;
    }



}

