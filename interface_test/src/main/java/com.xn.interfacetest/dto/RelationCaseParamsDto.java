/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * RelationCaseParams Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationCaseParamsDto extends BaseDto {
    
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
     * paramsId 
     */
    private Long paramsId;

    /**
     * 
     * value 
     */
    private String value;

    /**
     * 
     * interfaceId 
     */
    private Long interfaceId;

    /**
     * 
     * caseId 
     */
    private Long caseId;

    /**
     * 
     * Header里面的参数-1，Body里面的参数-2 
     */
    private Integer type;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setParamsId(Long paramsId) {
        this.paramsId = paramsId;
    }
    
    public Long getParamsId() {
        return this.paramsId;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }



}

