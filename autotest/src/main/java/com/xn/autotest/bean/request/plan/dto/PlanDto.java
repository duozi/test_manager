/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.plan.dto;

import java.util.Date;
import com.xn.autotest.bean.BaseDto;


/**
 * Plan Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class PlanDto extends BaseDto{
    
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
     * 计划名称 
     */
    private String planName;

    /**
     * 
     * 计划的类型,0表示单接口,1表示串行接口 
     */
    private Boolean executeType;

    /**
     * 
     * 计划是否可用,1表示可用,0表示不可以用 
     */
    private Boolean planStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 计划是否发布的类型，0表示未发布，可以修改，1表示已发布不能修改 
     */
    private Boolean planType;

    /**
     * 
     * 计划下面的系统名，多个用，隔开 
     */
    private String systemName;

    
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

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public String getPlanName() {
        return this.planName;
    }

    public void setExecuteType(Boolean executeType) {
        this.executeType = executeType;
    }
    
    public Boolean getExecuteType() {
        return this.executeType;
    }

    public void setPlanStatus(Boolean planStatus) {
        this.planStatus = planStatus;
    }
    
    public Boolean getPlanStatus() {
        return this.planStatus;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setPlanType(Boolean planType) {
        this.planType = planType;
    }
    
    public Boolean getPlanType() {
        return this.planType;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public String getSystemName() {
        return this.systemName;
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

