/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * TestSuit 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestSuit extends BaseEntity {

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

    private Long systemId;
    /**
     * 是否被删除0-否，1-是
     */
    private Integer isDelete;

	public TestSuit(){
	    // default constructor
	}
    
    public TestSuit(Long id){
        this.id = id;
    }

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
    
    public String getCreatePerson() {
        return this.createPerson;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
