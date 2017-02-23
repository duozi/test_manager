/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * RelationSuitCase Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationSuitCaseDto extends BaseDto {
    
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
     * suitId 
     */
    private Long suitId;

    /**
     * 
     * interfaceId 
     */
    private Long interfaceId;

    /**
     * 
     * serviceId 
     */
    private Long serviceId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }
    
    public Long getSuitId() {
        return this.suitId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public Long getServiceId() {
        return this.serviceId;
    }



}

