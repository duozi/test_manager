/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.manage.entity;

/**
 * Result 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class Result extends BaseEntity {

    /**
     * 主键列
     * id 
     */
    private Integer id;

    /**
     * 
     * 计划名 
     */
    private String planName;

    /**
     * 
     * 计划中系统的名称，用json表示 
     */
    private String systemName;

    /**
     * 
     * 接口名，用json表示 
     */
    private String interfaceName;

    /**
     * 
     * 结果用json表示 
     */
    private String result;


	
	public Result(){
	    // default constructor
	}
    
    public Result(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public String getPlanName() {
        return this.planName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public String getSystemName() {
        return this.systemName;
    }
    
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getInterfaceName() {
        return this.interfaceName;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getResult() {
        return this.result;
    }
    


}
