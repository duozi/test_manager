/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import java.util.ArrayList;
import java.util.List;

import com.xn.common.base.BaseEntity;

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
     * 参数是自动亿还是按照字段配的
     */
    private Integer paramsType;

    /**
     *
     * 自定义参数
     */
    private String customParams;


    /**
     *
     * 自定义参数类型
     */
    private Integer customParamsType;

    /**
     * 是否被删除0-否，1-是
     */
    private Integer isDelete;

    /**
     * 状态
     */
    private int status;

    private List<DataAssert> dataAsserts = new ArrayList<DataAssert>();
    
    private List<RedisAssert> redisAsserts = new ArrayList<RedisAssert>();
    
    private List<ParamsAssert> paramsAsserts = new ArrayList<ParamsAssert>();

	
	public TestCase(){
	    // default constructor
	}
    
    public TestCase(Long id){
        this.id = id;
    }

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
    
    public List<DataAssert> getDataAsserts() {
        return dataAsserts;
    }
    
    public void setDataAsserts(List<DataAssert> dataAsserts){
        this.dataAsserts = dataAsserts;
    }
    
    public List<RedisAssert> getRedisAsserts() {
        return redisAsserts;
    }
    
    public void setRedisAsserts(List<RedisAssert> redisAsserts){
        this.redisAsserts = redisAsserts;
    }
    
    public List<ParamsAssert> getParamsAsserts() {
        return paramsAsserts;
    }
    
    public void setParamsAsserts(List<ParamsAssert> paramsAsserts){
        this.paramsAsserts = paramsAsserts;
    }

    public String getCustomParams() {
        return customParams;
    }

    public void setCustomParams(String customParams) {
        this.customParams = customParams;
    }

    public Integer getCustomParamsType() {
        return customParamsType;
    }

    public void setCustomParamsType(Integer customParamsType) {
        this.customParamsType = customParamsType;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getParamsType() {
        return paramsType;
    }

    public void setParamsType(Integer paramsType) {
        this.paramsType = paramsType;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", interfaceId=" + interfaceId +
                ", type='" + type + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", dataPrepare=" + dataPrepare +
                ", dataClear=" + dataClear +
                ", paramsAssert=" + paramsAssert +
                ", databaseAssert=" + databaseAssert +
                ", redisAssert=" + redisAssert +
                ", paramsType=" + paramsType +
                ", customParams='" + customParams + '\'' +
                ", customParamsType=" + customParamsType +
                ", isDelete=" + isDelete +
                ", status=" + status +
                ", dataAsserts=" + dataAsserts +
                ", redisAsserts=" + redisAsserts +
                ", paramsAsserts=" + paramsAsserts +
                '}';
    }
}
