/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * TestCase Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestCaseDto extends BaseDto {
    
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
     * 接口名称 
     */
    private String name;

    /**
     * 
     * 用例编号 
     */
    private String number;

    /**
     * 
     * 接口描述 
     */
    private String description;

    /**
     * 
     * 接口id 
     */
    private Long interfaceId;

    /**
     * 
         * 用例类型（SINGLE-单接口，MULTIPLE-集成接口）
     */
    private String type;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    /**
     * 
     * 数据准备（1-有数据准备，0-没有数据准备） 
     */
    private Integer dataPrepare;

    /**
     * 
     * 是否有数据清除（1-有，0-没有） 
     */
    private Integer dataClear;

    /**
     * 
     * 是否有返回参数断言（1-有，0-没有） 
     */
    private Integer paramsAssert;

    /**
     * 
     * 是否有数据库断言（1-有，0-没有） 
     */
    private Integer databaseAssert;

    /**
     * 
     * 是否有redis断言（1-有，0-没有） 
     */
    private Integer redisAssert;


    /**
     *
     * header里面的自定义参数
     */
    private String customParamsHeader;

    /**
     *
     * body里面的自定义参数
     */
    private String customParamsBody;

    /**
     *
     * header里面的自定义参数类型
     */
    private Boolean customParamsHeaderType;

    /**
     *
     * body里面的自定义参数类型
     */
    private Boolean customParamsBodyType;


    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Integer getDataPrepare() {
        return this.dataPrepare;
    }
    
    public void setDataPrepare(Integer dataPrepare) {
        this.dataPrepare = dataPrepare;
    }

    public Integer getDataClear() {
        return this.dataClear;
    }
    
    public void setDataClear(Integer dataClear) {
        this.dataClear = dataClear;
    }

    public Integer getParamsAssert() {
        return this.paramsAssert;
    }
    
    public void setParamsAssert(Integer paramsAssert) {
        this.paramsAssert = paramsAssert;
    }

    public Integer getDatabaseAssert() {
        return this.databaseAssert;
    }
    
    public void setDatabaseAssert(Integer databaseAssert) {
        this.databaseAssert = databaseAssert;
    }

    public Integer getRedisAssert() {
        return this.redisAssert;
    }
    
    public void setRedisAssert(Integer redisAssert) {
        this.redisAssert = redisAssert;
    }

    public String getCustomParamsHeader() {
        return customParamsHeader;
    }

    public void setCustomParamsHeader(String customParamsHeader) {
        this.customParamsHeader = customParamsHeader;
    }

    public String getCustomParamsBody() {
        return customParamsBody;
    }

    public void setCustomParamsBody(String customParamsBody) {
        this.customParamsBody = customParamsBody;
    }

    public Boolean getCustomParamsHeaderType() {
        return customParamsHeaderType;
    }

    public void setCustomParamsHeaderType(Boolean customParamsHeaderType) {
        this.customParamsHeaderType = customParamsHeaderType;
    }

    public Boolean getCustomParamsBodyType() {
        return customParamsBodyType;
    }

    public void setCustomParamsBodyType(Boolean customParamsBodyType) {
        this.customParamsBodyType = customParamsBodyType;
    }
}

