/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dto;

import com.xn.common.base.BaseDto;


/**
 * 角色资源关联表 Dto 对象
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class RoleResourcesDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * 主键 
     */
    private Long id;

    /**
     * 
     * 角色id 
     */
    private Long roleId;

    /**
     * 
     * 资源id 
     */
    private Long sourceId;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Long getRoleId() {
        return this.roleId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
    
    public Long getSourceId() {
        return this.sourceId;
    }



}

