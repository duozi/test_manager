/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * RedisAssert Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RedisAssertDto extends BaseDto {
    
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
     * 数据库id 
     */
    private Long redisConfigId;

    /**
     *
     * keyStr
     */
    private String keyStr;

    /**
     * 
     * 校验字段 
     */
    private String assertParam;

    /**
     * 
     * 期望值 
     */
    private String rightValue;

    /**
     * 
     * 用例id 
     */
    private Long caseId;

    /**
     * 缓存类型1-时间，2-值
     */
    private Integer type;

    /**
     * 是否被删除0-否，1-是
     */
    private Integer isDelete;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedisConfigId() {
        return this.redisConfigId;
    }
    
    public void setRedisConfigId(Long redisConfigId) {
        this.redisConfigId = redisConfigId;
    }

    public String getAssertParam() {
        return this.assertParam;
    }
    
    public void setAssertParam(String assertParam) {
        this.assertParam = assertParam;
    }

    public String getRightValue() {
        return this.rightValue;
    }
    
    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }

    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}

