/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * TestCase 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestCase extends BaseEntity {

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
     * 用例类型（1-单接口，2-集成接口） 
     */
    private Integer type;

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

    
    private List<DataAssert> dataAsserts = new ArrayList<DataAssert>();
    
    private List<RedisAssert> redisAsserts = new ArrayList<RedisAssert>();
    
    private List<ParamsAssert> paramsAsserts = new ArrayList<ParamsAssert>();

	
	public TestCase(){
	    // default constructor
	}
    
    public TestCase(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getNumber() {
        return this.number;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    public String getCreatePerson() {
        return this.createPerson;
    }
    
    public void setDataPrepare(Integer dataPrepare) {
        this.dataPrepare = dataPrepare;
    }
    
    public Integer getDataPrepare() {
        return this.dataPrepare;
    }
    
    public void setDataClear(Integer dataClear) {
        this.dataClear = dataClear;
    }
    
    public Integer getDataClear() {
        return this.dataClear;
    }
    
    public void setParamsAssert(Integer paramsAssert) {
        this.paramsAssert = paramsAssert;
    }
    
    public Integer getParamsAssert() {
        return this.paramsAssert;
    }
    
    public void setDatabaseAssert(Integer databaseAssert) {
        this.databaseAssert = databaseAssert;
    }
    
    public Integer getDatabaseAssert() {
        return this.databaseAssert;
    }
    
    public void setRedisAssert(Integer redisAssert) {
        this.redisAssert = redisAssert;
    }
    
    public Integer getRedisAssert() {
        return this.redisAssert;
    }
    

    
    public void setDataAsserts(List<DataAssert> dataAsserts){
        this.dataAsserts = dataAsserts;
    }
    
    public List<DataAssert> getDataAsserts() {
        return dataAsserts;
    }
    
    public void setRedisAsserts(List<RedisAssert> redisAsserts){
        this.redisAsserts = redisAsserts;
    }
    
    public List<RedisAssert> getRedisAsserts() {
        return redisAsserts;
    }
    
    public void setParamsAsserts(List<ParamsAssert> paramsAsserts){
        this.paramsAsserts = paramsAsserts;
    }
    
    public List<ParamsAssert> getParamsAsserts() {
        return paramsAsserts;
    }

}
