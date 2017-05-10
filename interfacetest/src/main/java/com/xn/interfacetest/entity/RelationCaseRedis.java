/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.base.BaseEntity;

/**
 * RelationCaseRedis 实体类
 * 
 * @author Carol
 * @date 2017-03-02
 */
public class RelationCaseRedis extends BaseEntity {

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
     * 数redis名称
     */
    private String redisName;
    /**
     * 
     * 操作类型（1-数据准备，2-数据清除） 
     */
    private Integer operateType;

    /**
     * 
     * Redis操作类型 
     */
    private Integer redisOperateType;

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
    private Integer time;

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

    private TestRedisConfig testRedisConfig;

    /**
     * 是否被删除0-否，1-是
     */
    private Integer isDelete;
	
	public RelationCaseRedis(){
	    // default constructor
	}
    
    public RelationCaseRedis(Long id){
        this.id = id;
    }

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

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public Integer getOperateType() {
        return this.operateType;
    }
    
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    
    public Integer getRedisOperateType() {
        return this.redisOperateType;
    }
    
    public void setRedisOperateType(Integer redisOperateType) {
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
    
    public Integer getTime() {
        return this.time;
    }
    
    public void setTime(Integer time) {
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

    public TestRedisConfig getTestRedisConfig() {
        return testRedisConfig;
    }
    
    public void setTestRedisConfig(TestRedisConfig testRedisConfig){
        this.testRedisConfig = testRedisConfig;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
