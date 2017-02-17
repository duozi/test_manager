/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.entity.BaseEntity;

/**
 * TestParams 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestParams extends BaseEntity {

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
     * 参数名 
     */
    private String name;

    /**
     * 
     * 参数类型（普通参数-1，特殊格式参数-2） 
     */
    private Integer formatType;

    /**
     * 
     * 接口id 
     */
    private Long interfaceId;

    /**
     * 
     * 数据类型（int,string….） 
     */
    private String dataType;

    /**
     * 
     * 1-Header参数，2-body参数 
     */
    private Integer type;


    
    private TestInterface testInterface;
   
	
	public TestParams(){
	    // default constructor
	}
    
    public TestParams(Long id){
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
    
    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }
    
    public Integer getFormatType() {
        return this.formatType;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public String getDataType() {
        return this.dataType;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    


    
    public void setTestInterface(TestInterface testInterface){
        this.testInterface = testInterface;
    }
    
    public TestInterface getTestInterface() {
        return testInterface;
    }
}
