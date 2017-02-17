/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.company.entity;


import com.xn.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Company 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class Company extends BaseEntity {

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

    
    private List<Department> departments = new ArrayList<Department>();

	
	public Company(){
	    // default constructor
	}
    
    public Company(Long id){
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
    

    
    public void setDepartments(List<Department> departments){
        this.departments = departments;
    }
    
    public List<Department> getDepartments() {
        return departments;
    }

}
