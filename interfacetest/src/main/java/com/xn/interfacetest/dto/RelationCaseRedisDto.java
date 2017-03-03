/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;

import com.xn.common.base.BaseDto;

/**
 * RelationCaseRedis Dto 对象
 * 
 * @author Carol
 * @date 2017-03-02
 */
public class RelationCaseRedisDto extends BaseDto {
    
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
     * redisId 
     */
    private Long redisId;

    /**
     * 
     * 操作类型（1-数据准备，2-数据清除） 
     */
    private Integer operateType;

    /**
     * 
     * Redis操作类型 
     */
    private Boolean redisOperateType;

    /**
     * 
     * key 
     */
    private String key;

    /**
     * 
     * value 
     */
    private String value;

    /**
     * 
     * time 
     */
    private Long time;

    /**
     *
     * 接口id
     */
    private Long interfaceId;

    /**
     *
     * 1-接口数据处理，2-用例数据处理
     */
    private Integer type;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getRedisId() {
        return this.redisId;
    }
    
    public void setRedisId(Long redisId) {
        this.redisId = redisId;
    }

    public Integer getOperateType() {
        return this.operateType;
    }
    
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Boolean getRedisOperateType() {
        return this.redisOperateType;
    }
    
    public void setRedisOperateType(Boolean redisOperateType) {
        this.redisOperateType = redisOperateType;
    }

    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public Long getTime() {
        return this.time;
    }
    
    public void setTime(Long time) {
        this.time = time;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

