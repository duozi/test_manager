/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.parameterAssert.dto;

import java.util.Date;
import com.xn.autotest.bean.BaseDto;


/**
 * ParameterAssert Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class ParameterAssertDto extends BaseDto{
    
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
     * 所属的计划id 
     */
    private Integer caseId;

    /**
     * 
     * 参数校验是否可用，1表示可用，0表示不可用 
     */
    private Boolean parameterAssertStatus;

    /**
     * 
     * 参数校验内容，用json表示 
     */
    private String parameterAssertContent;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
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

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
    public Integer getCaseId() {
        return this.caseId;
    }

    public void setParameterAssertStatus(Boolean parameterAssertStatus) {
        this.parameterAssertStatus = parameterAssertStatus;
    }
    
    public Boolean getParameterAssertStatus() {
        return this.parameterAssertStatus;
    }

    public void setParameterAssertContent(String parameterAssertContent) {
        this.parameterAssertContent = parameterAssertContent;
    }
    
    public String getParameterAssertContent() {
        return this.parameterAssertContent;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
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

