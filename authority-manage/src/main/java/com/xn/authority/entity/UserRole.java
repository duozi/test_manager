/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.entity;


import com.xn.common.base.BaseEntity;

/**
 * 用户角色关联表 实体类
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class UserRole extends BaseEntity {

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
     * 用户id 
     */
    private Long userId;

    /**
     * 
     * 角色id 
     */
    private Long roleId;


	
	public UserRole(){
	    // default constructor
	}
    
    public UserRole(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Long getRoleId() {
        return this.roleId;
    }
    


}
