/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.interfacetest.base.BaseDto;

/**
 * RelationPlanEnvironment Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationPlanEnvironmentDto extends BaseDto {
    
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
     * environmentId 
     */
    private Long environmentId;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getEnvironmentId() {
        return this.environmentId;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }



}

