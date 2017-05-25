/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.entity;

import java.util.Date;
import com.xn.common.base.BaseEntity;

/**
 * 用户表 实体类
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class User extends BaseEntity {

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
     * 账号 
     */
    private String account;

    /**
     * 
     * 密码 
     */
    private String password;

    /**
     * 
     * 状态【启用Y、禁用N】 
     */
    private String status;

    /**
     * 
     * 0、子账号 1、主账号 
     */
    private Integer primary;

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

    // 角色名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public User(){
	    // default constructor
	}
    
    public User(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getAccount() {
        return this.account;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }

    public Integer getPrimary() {
        return primary;
    }

    public void setPrimary(Integer primary) {
        this.primary = primary;
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
