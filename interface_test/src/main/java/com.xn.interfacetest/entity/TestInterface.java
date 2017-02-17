/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.entity;


import com.xn.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * TestInterface 实体类
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestInterface extends BaseEntity {

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
     * 接口描述 
     */
    private String description;

    /**
     * 
     * 服务id 
     */
    private Long serviceId;

    /**
     * 
     * 接口类型（1-http，2-dubbo） 
     */
    private Integer type;

    /**
     * 
     * 接口路径 
     */
    private String url;

    /**
     * 
     * 解密代码块 
     */
    private String codeText;

    /**
     * 
     * 加密jar包路径 
     */
    private String jarPath;

    /**
     * 
     * 请求方式（1-get,2-post） 
     */
    private Integer requestType;

    /**
     * 
     * 协议类型（1-http，2-https） 
     */
    private Integer protocolType;

    /**
     * 
     * 参数，以逗号隔开 
     */
    private String params;

    /**
     * 
     * Dubbo版本 
     */
    private Double dubboVersion;

    /**
     * 
     * Dubbo分组 
     */
    private String dubboGroup;

    /**
     * 
     * Dubbo的超时时间(s) 
     */
    private Long dubboTimeout;

    
    private List<TestParams> testParamss = new ArrayList<TestParams>();

	
	public TestInterface(){
	    // default constructor
	}
    
    public TestInterface(Long id){
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
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public Long getServiceId() {
        return this.serviceId;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }
    
    public String getCodeText() {
        return this.codeText;
    }
    
    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
    
    public String getJarPath() {
        return this.jarPath;
    }
    
    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }
    
    public Integer getRequestType() {
        return this.requestType;
    }
    
    public void setProtocolType(Integer protocolType) {
        this.protocolType = protocolType;
    }
    
    public Integer getProtocolType() {
        return this.protocolType;
    }
    
    public void setParams(String params) {
        this.params = params;
    }
    
    public String getParams() {
        return this.params;
    }
    
    public void setDubboVersion(Double dubboVersion) {
        this.dubboVersion = dubboVersion;
    }
    
    public Double getDubboVersion() {
        return this.dubboVersion;
    }
    
    public void setDubboGroup(String dubboGroup) {
        this.dubboGroup = dubboGroup;
    }
    
    public String getDubboGroup() {
        return this.dubboGroup;
    }
    
    public void setDubboTimeout(Long dubboTimeout) {
        this.dubboTimeout = dubboTimeout;
    }
    
    public Long getDubboTimeout() {
        return this.dubboTimeout;
    }
    

    
    public void setTestParamss(List<TestParams> testParamss){
        this.testParamss = testParamss;
    }
    
    public List<TestParams> getTestParamss() {
        return testParamss;
    }

}
