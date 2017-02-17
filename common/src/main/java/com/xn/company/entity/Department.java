/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.company.entity;


import com.xn.common.entity.BaseEntity;

/**
 * Department 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class Department extends BaseEntity {

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
     * name 
     */
    private String name;

    /**
     * 
     * companyId 
     */
    private Long companyId;


    
    private Company company;
   
	
	public Department(){
	    // default constructor
	}
    
    public Department(Long id){
        this.id = id;
    }

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
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public Long getCompanyId() {
        return this.companyId;
    }
    


    
    public void setCompany(Company company){
        this.company = company;
    }
    
    public Company getCompany() {
        return company;
    }
}
