/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.entity;

import com.xn.common.base.BaseEntity;

import java.util.Date;


/**
 * 角色表 实体类
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class Role extends BaseEntity {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键列
     * 自增主键 
     */
    private Long id;

    /**
     * 
     * 角色名称 
     */
    private String name;

    /**
     * 
     * 状态【Y:启用 N:禁用】 
     */
    private String status;

    /**
     * 
     * 备注 
     */
    private String remark;

    /**
     * 
     * 创建时间 
     */
    private Date createTime;

    /**
     * 
     * 修改时间 
     */
    private Date updateTime;


	
	public Role(){
	    // default constructor
	}
    
    public Role(Long id){
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
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Date getUpdateTime() {
        return this.updateTime;
    }
    


}
