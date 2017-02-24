/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.entity.BaseDto;

/**
 * RelationCaseDatabase Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationCaseDatabaseDto extends BaseDto {
    
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
     * caseId 
     */
    private Long caseId;

    /**
     * 
     * databaseId 
     */
    private Long databaseId;

    /**
     * 
     * 操作类型（1-数据准备，2-数据清除） 
     */
    private Integer operateType;

    /**
     * 
     * sqlStr 
     */
    private String sqlStr;

    

    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public Long getCaseId() {
        return this.caseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
    
    public Long getDatabaseId() {
        return this.databaseId;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    
    public Integer getOperateType() {
        return this.operateType;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }
    
    public String getSqlStr() {
        return this.sqlStr;
    }



}

