/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.http.entity;

import com.xn.autotest.bean.BaseEntity;
import com.xn.autotest.bean.request.interfacesBean.entity.Interface;

import java.util.Date;

/**
 * HttpServiceProperties 实体类
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public class HttpServiceProperties extends BaseEntity {

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
     * 地址 
     */
    private String url;

    /**
     * 
     * 端口 
     */
    private String port;

    /**
     * 
     * 超时（毫秒） 
     */
    private String timeout;

    /**
     * 
     * post/get 
     */
    private String requestType;

    /**
     * 
     * 请求参数类型 text/json 
     */
    private String paramType;

    /**
     * 
     * http配置是否可用，1可用,0不可用 
     */
    private Boolean httpStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 接口名 
     */
    private String interfaceName;


    
    private Interface interfaceBean;
   
	
	public HttpServiceProperties(){
	    // default constructor
	}
    
    public HttpServiceProperties(Integer id){
        this.id = id;
    }

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
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getPort() {
        return this.port;
    }
    
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    
    public String getTimeout() {
        return this.timeout;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public String getRequestType() {
        return this.requestType;
    }
    
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
    
    public String getParamType() {
        return this.paramType;
    }
    
    public void setHttpStatus(Boolean httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public Boolean getHttpStatus() {
        return this.httpStatus;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getInterfaceName() {
        return this.interfaceName;
    }
    


    
    public void setInterface(Interface interfaceBean){
        this.interfaceBean = interfaceBean;
    }
    
    public Interface getInterface() {
        return interfaceBean;
    }
}
