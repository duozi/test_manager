/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * TimeConfig Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TimeConfigDto extends BaseDto {
    
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
     * 执行时间描述 
     */
    private String description;

    /**
     * 
     * 时间表达式 
     */
    private String timeExpression;

    /**
     * 
     * 计划id 
     */
    private Long planId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }

    public void setTimeExpression(String timeExpression) {
        this.timeExpression = timeExpression;
    }
    
    public String getTimeExpression() {
        return this.timeExpression;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    public Long getPlanId() {
        return this.planId;
    }



}

