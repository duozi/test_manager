/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dto;


import com.xn.common.base.BaseDto;

/**
 * TestParams Dto 对象
 * 
 * @author Carol
 * @date 2017-02-14
 */
public class TestParamsDto extends BaseDto {
    
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

    /**
     * 删除标记字段1-表示删除
     */
    private Integer isDelete;

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getFormatType() {
        return this.formatType;
    }
    
    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }

    public Long getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getDataType() {
        return this.dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}

