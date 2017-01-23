/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.manage.bean;

/**
 * Department 实体类
 * 
 * @author Carol
 * @date 2017-01-22
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
    private Integer id;

    /**
     * 
     * 部门 
     */
    private String name;

    /**
     * 
     * companyId 
     */
    private Integer companyId;


    
    private Company company;
   
	
	public Department(){
	    // default constructor
	}
    
    public Department(Integer id){
        this.id = id;
    }

    public Department(Integer id,String name){
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company){
        this.company = company;
    }
}
