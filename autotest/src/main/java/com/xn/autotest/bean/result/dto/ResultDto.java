/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.result.dto;

import com.xn.autotest.bean.BaseDto;


/**
 * Result Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class ResultDto extends BaseDto{
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
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

