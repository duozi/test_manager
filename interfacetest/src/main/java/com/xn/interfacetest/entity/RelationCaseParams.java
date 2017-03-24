/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * RelationCaseParams 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationCaseParams extends BaseEntity {

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

    /**
     * 是否被删除0-否，1-是
     */
	private Integer isDelete;

	public RelationCaseParams(){
	    // default constructor
	}
    
    public RelationCaseParams(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParamsId() {
        return this.paramsId;
    }
    
    public void setParamsId(Long paramsId) {
        this.paramsId = paramsId;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
