/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.cases.dto;

import java.util.Date;
import com.xn.autotest.bean.BaseDto;


/**
 * Cases Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class CasesDto extends BaseDto{
    
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
     * 对应的方法id 
     */
    private Integer methodId;

    /**
     * 
     * case名称 
     */
    private String caseName;

    /**
     * 
     * case是否可用,1表示可用,0表示不可以用 
     */
    private Boolean caseStatus;

    /**
     * 
     * case的参数，用json表示 
     */
    private String param;

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

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }
    
    public Integer getMethodId() {
        return this.methodId;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    
    public String getCaseName() {
        return this.caseName;
    }

    public void setCaseStatus(Boolean caseStatus) {
        this.caseStatus = caseStatus;
    }
    
    public Boolean getCaseStatus() {
        return this.caseStatus;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
    public String getParam() {
        return this.param;
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

