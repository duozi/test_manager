/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.manage.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * Company 实体类
 * 
 * @author Carol
 * @date 2017-01-22
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
    private Integer id;

    /**
     * 
     * 公司名称 
     */
    private String name;

    
    private List<Department> departments = new ArrayList<Department>();

	
	public Company(){
	    // default constructor
	}
    public Company(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    
    public Company(Integer id){
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
    
    public List<Department> getDepartments() {
        return departments;
    }
    
    public void setDepartments(List<Department> departments){
        this.departments = departments;
    }

}
