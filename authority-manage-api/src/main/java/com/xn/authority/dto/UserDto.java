/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dto;

import java.util.Date;
import com.xn.common.base.BaseDto;


/**
 * 用户表 Dto 对象
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class UserDto extends BaseDto{
    
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

    
    /**
     * 创建时间
     */
    private Date createTimeBegin;

    /**
     * 创建时间
     */
    private Date createTimeEnd;

    /**
     * 修改时间
     */
    private Date updateTimeBegin;

    /**
     * 修改时间
     */
    private Date updateTimeEnd;


    
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


    public Date getCreateTimeBegin() {
        return this.createTimeBegin;
    }
    
    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }    
    
    public Date getCreateTimeEnd() {
        return this.createTimeEnd;
    }
    
    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
    
    public Date getUpdateTimeBegin() {
        return this.updateTimeBegin;
    }
    
    public void setUpdateTimeBegin(Date updateTimeBegin) {
        this.updateTimeBegin = updateTimeBegin;
    }    
    
    public Date getUpdateTimeEnd() {
        return this.updateTimeEnd;
    }
    
    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }
    

}

