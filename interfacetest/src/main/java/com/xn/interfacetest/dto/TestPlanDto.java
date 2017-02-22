/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * TestPlan Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestPlanDto extends BaseDto {
    
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
     * 计划名称 
     */
    private String name;

    /**
     * 
     * 计划描述 
     */
    private String description;

    /**
     * 
     * 计划状态（0-未发布，1-已发布未执行，2-已执行） 
     */
    private Integer status;

    /**
     * 
     * 执行方式（1-单次执行，2-循环定时执行） 
     */
    private Integer excuteType;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

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

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExcuteType() {
        return this.excuteType;
    }
    
    public void setExcuteType(Integer excuteType) {
        this.excuteType = excuteType;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }



}

