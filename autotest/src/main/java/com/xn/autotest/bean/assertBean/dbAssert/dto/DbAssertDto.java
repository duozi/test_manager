/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.dbAssert.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * DbAssert Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class DbAssertDto extends BaseDto{
    
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
     * 数据库校验是否可用，1表示可用，0表示不可用 
     */
    private Boolean parameterAssertStatus;

    /**
     * 
     * 执行的数据库名 
     */
    private String dbName;

    /**
     * 
     * 执行的sql语句 
     */
    private String sqlString;

    /**
     * 
     * 数据库校验内容，用json表示 
     */
    private String dbAssertContent;

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

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String getDbName() {
        return this.dbName;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }
    
    public String getSqlString() {
        return this.sqlString;
    }

    public void setDbAssertContent(String dbAssertContent) {
        this.dbAssertContent = dbAssertContent;
    }
    
    public String getDbAssertContent() {
        return this.dbAssertContent;
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

