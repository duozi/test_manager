/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.dubbo.dto;

import java.util.Date;
import com.xn.autotest.bean.BaseDto;


/**
 * DubboServiceProperties Dto 对象
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class DubboServicePropertiesDto extends BaseDto{
    
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
     * 对应接口id 
     */
    private Integer interfaceId;

    /**
     * 
     * dubbo 服务名 
     */
    private String appName;

    /**
     * 
     * 接口名 
     */
    private String interfaceName;

    /**
     * 
     * 地址 
     */
    private String url;

    /**
     * 
     * 分组 
     */
    private String groupName;

    /**
     * 
     * 版本 
     */
    private String version;

    /**
     * 
     * 超时（毫秒） 
     */
    private String timeout;

    /**
     * 
     * 是否使用zk,1表示使用，0表示不使用（使用url直接调用） 
     */
    private Boolean useZk;

    /**
     * 
     * zk 
     */
    private String zk;

    /**
     * 
     * 配置是否可用 1可用，0不可用 
     */
    private Boolean dubboStatus;

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

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Integer getInterfaceId() {
        return this.interfaceId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getAppName() {
        return this.appName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getInterfaceName() {
        return this.interfaceName;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getGroupName() {
        return this.groupName;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getVersion() {
        return this.version;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    
    public String getTimeout() {
        return this.timeout;
    }

    public void setUseZk(Boolean useZk) {
        this.useZk = useZk;
    }
    
    public Boolean getUseZk() {
        return this.useZk;
    }

    public void setZk(String zk) {
        this.zk = zk;
    }
    
    public String getZk() {
        return this.zk;
    }

    public void setDubboStatus(Boolean dubboStatus) {
        this.dubboStatus = dubboStatus;
    }
    
    public Boolean getDubboStatus() {
        return this.dubboStatus;
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

