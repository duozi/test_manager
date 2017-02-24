/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * RelationServiceEnvironment Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class RelationServiceEnvironmentDto extends BaseDto {
    
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
     * serviceId 
     */
    private Long serviceId;

    /**
     * 
     * environmentId 
     */
    private Long environmentId;

    /**
     * 
     * zkIpAddress 
     */
    private String zkIpAddress;

    /**
     * 
     * ipAddress 
     */
    private String ipAddress;

    /**
     * 
     * httpPort 
     */
    private String httpPort;

    /**
     * 
     * dubboPort 
     */
    private String dubboPort;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return this.serviceId;
    }
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getEnvironmentId() {
        return this.environmentId;
    }
    
    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public String getZkIpAddress() {
        return this.zkIpAddress;
    }
    
    public void setZkIpAddress(String zkIpAddress) {
        this.zkIpAddress = zkIpAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHttpPort() {
        return this.httpPort;
    }
    
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    public String getDubboPort() {
        return this.dubboPort;
    }
    
    public void setDubboPort(String dubboPort) {
        this.dubboPort = dubboPort;
    }



}

