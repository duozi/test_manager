/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

import java.sql.Timestamp;

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
     * 时间表达式 ，若执行时间类型是单次执行，则将具体的执行时间写入时间表达式中
     */
    private String timeExpression;

    /**
     * 
     * 计划id 
     */
    private Long planId;

    /**
     * 执行时间
     */
    private String excuteTime;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeExpression() {
        return this.timeExpression;
    }
    
    public void setTimeExpression(String timeExpression) {
        this.timeExpression = timeExpression;
    }

    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }
}

