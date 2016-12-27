/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.system.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * System Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class SystemDto extends BaseDto{
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的计划id 
     */
    private Integer planId;

    /**
     * 
     * 系统名称 
     */
    private String systemName;

    /**
     * 
     * 系统的类型,0表示http,1表示dubbo 
     */
    private Integer type;

    /**
     * 
     * 系统是否可用,1表示可用,0表示不可以用 
     */
    private Integer systemStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 是否上传jar包，1表示上传，0表示没有上传 
     */
    private Integer jarStatus;

    
    /**
     * changeTime
     */
    private Date changeTimeBegin;

    /**
     * changeTime
     */
    private Date changeTimeEnd;


    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    
    public Integer getPlanId() {
        return this.planId;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public String getSystemName() {
        return this.systemName;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }

    public void setSystemStatus(Integer systemStatus) {
        this.systemStatus = systemStatus;
    }
    
    public Integer getSystemStatus() {
        return this.systemStatus;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setJarStatus(Integer jarStatus) {
        this.jarStatus = jarStatus;
    }
    
    public Integer getJarStatus() {
        return this.jarStatus;
    }


    public Date getChangeTimeBegin() {
        return this.changeTimeBegin;
    }
    
    public void setChangeTimeBegin(Date changeTimeBegin) {
        this.changeTimeBegin = changeTimeBegin;
    }    
    
    public Date getChangeTimeEnd() {
        return this.changeTimeEnd;
    }
    
    public void setChangeTimeEnd(Date changeTimeEnd) {
        this.changeTimeEnd = changeTimeEnd;
    }
    

}

