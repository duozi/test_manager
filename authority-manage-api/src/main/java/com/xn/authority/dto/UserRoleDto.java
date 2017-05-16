/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dto;


import com.xn.common.base.BaseDto;

/**
 * 用户角色关联表 Dto 对象
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class UserRoleDto extends BaseDto {
    
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

