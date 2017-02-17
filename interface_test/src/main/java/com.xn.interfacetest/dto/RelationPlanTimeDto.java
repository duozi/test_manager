/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * RelationPlanTime Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationPlanTimeDto extends BaseDto {
    
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
     * planId 
     */
    private Long planId;

    /**
     * 
     * timeConfigId 
     */
    private Long timeConfigId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    public Long getPlanId() {
        return this.planId;
    }

    public void setTimeConfigId(Long timeConfigId) {
        this.timeConfigId = timeConfigId;
    }
    
    public Long getTimeConfigId() {
        return this.timeConfigId;
    }



}

