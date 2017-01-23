/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.manage.bean;

/**
 * System 实体类
 * 
 * @author Carol
 * @date 2017-01-22
 */
public class System extends BaseEntity {

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
     * 系统名 
     */
    private String name;

    /**
     * 
     * 部门id 
     */
    private Integer departmentId;


	
	public System(){
	    // default constructor
	}
    
    public System(Integer id){
        this.id = id;
    }

    public System(Integer id,String name){
        this.id = id;
        this.name = name;
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
    
    public Integer getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    


}
