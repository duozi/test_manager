/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.entity.BaseEntity;

import java.util.Date;

/**
 * RelationPlanResult 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationPlanResult extends BaseEntity {

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
     * planId 
     */
    private Long planId;

    /**
     * 
     * reportId 
     */
    private Long reportId;

    /**
     * 
     * createTime 
     */
    private Date createTime;


	
	public RelationPlanResult(){
	    // default constructor
	}
    
    public RelationPlanResult(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    public Long getReportId() {
        return this.reportId;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    


}
