/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.base.BaseEntity;

/**
 * TestJarMethod 实体类
 * 
 * @author Carol
 * @date 2017-05-05
 */
public class TestJarMethod extends BaseEntity {

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
     * methodName 
     */
    private String methodName;

    /**
     * 
     * className 
     */
    private String className;

    /**
     * 
     * 参数类型 
     */
    private String paramsTypes;

    /**
     * 
     * 参数值 
     */
    private String paramsValues;

    /**
     * 
     * 接口id 
     */
    private Long interfaceId;


    
    private TestInterface testInterface;
   
	
	public TestJarMethod(){
	    // default constructor
	}
    
    public TestJarMethod(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public void setParamsTypes(String paramsTypes) {
        this.paramsTypes = paramsTypes;
    }
    
    public String getParamsTypes() {
        return this.paramsTypes;
    }
    
    public void setParamsValues(String paramsValues) {
        this.paramsValues = paramsValues;
    }
    
    public String getParamsValues() {
        return this.paramsValues;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Long getInterfaceId() {
        return this.interfaceId;
    }
    


    
    public void setTestInterface(TestInterface testInterface){
        this.testInterface = testInterface;
    }
    
    public TestInterface getTestInterface() {
        return testInterface;
    }
}
