/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.interfacesBean.dto;

import com.xn.autotest.bean.BaseDto;

import java.util.Date;


/**
 * Interface Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class InterfaceDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的系统id 
     */
    private Integer systemId;

    /**
     * 
     * 接口名称 
     */
    private String interfaceName;

    /**
     * 
     * 接口的类型,0表示http,1表示dubbo 
     */
    private Boolean interfaceType;

    /**
     * 
     * 接口是否可用,1表示可用,0表示不可以用 
     */
    private Boolean interfaceStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    /**
     * changeTime
     */
    private Date changeTimeBegin;

    /**
     * changeTime
     */
    private Date changeTimeEnd;


    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
    
    public Integer getSystemId() {
        return this.systemId;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getInterfaceName() {
        return this.interfaceName;
    }

    public void setInterfaceType(Boolean interfaceType) {
        this.interfaceType = interfaceType;
    }
    
    public Boolean getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceStatus(Boolean interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
    }
    
    public Boolean getInterfaceStatus() {
        return this.interfaceStatus;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }


    public Date getChangeTimeBegin() {
        return this.changeTimeBegin;
    }
    
    public void setChangeTimeBegin(Date changeTimeBegin) {
        this.changeTimeBegin = changeTimeBegin;
    }    
    
    public Date getChangeTimeEnd() {
        return this.changeTimeEnd;
    }
    
    public void setChangeTimeEnd(Date changeTimeEnd) {
        this.changeTimeEnd = changeTimeEnd;
    }
    

}

