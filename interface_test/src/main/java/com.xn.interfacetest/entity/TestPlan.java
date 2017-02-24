/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;

import com.xn.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * TestPlan 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestPlan extends BaseEntity {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键列
     * 自增长id 
     */
    private Long id;

    /**
     * 
     * 计划名称 
     */
    private String name;

    /**
     * 
     * 计划描述 
     */
    private String description;

    /**
     * 
     * 计划状态（0-未发布，1-已发布未执行，2-已执行） 
     */
    private Integer status;

    /**
     * 
     * 执行方式（1-单次执行，2-循环定时执行） 
     */
    private Integer excuteType;

    /**
     * 
     * 创建人 
     */
    private String createPerson;

    
    private List<TimeConfig> timeConfigs = new ArrayList<TimeConfig>();

	
	public TestPlan(){
	    // default constructor
	}
    
    public TestPlan(Long id){
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getStatus() {
        return this.status;
    }
    
    public void setExcuteType(Integer excuteType) {
        this.excuteType = excuteType;
    }
    
    public Integer getExcuteType() {
        return this.excuteType;
    }
    
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    public String getCreatePerson() {
        return this.createPerson;
    }
    

    
    public void setTimeConfigs(List<TimeConfig> timeConfigs){
        this.timeConfigs = timeConfigs;
    }
    
    public List<TimeConfig> getTimeConfigs() {
        return timeConfigs;
    }

}
