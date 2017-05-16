/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dto;


import com.xn.common.base.BaseDto;

import java.util.Date;
import java.util.List;

/**
 * 菜单资源表 Dto 对象
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public class ResourcesDto extends BaseDto {
    
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键列
     * 主键 
     */
    private Long id;

    /**
     * 
     * 父节点id 
     */
    private Long pid;

    /**
     * 
     * 菜单名称 
     */
    private String name;

    /**
     * 
     * 类型:0:菜单,1:按钮 
     */
    private Boolean srcType;

    /**
     * 
     * 排序 
     */
    private Integer sort;

    /**
     * 
     * URL地址 
     */
    private String url;

    /**
     * 
     * 状态【启用Y、禁用N】 
     */
    private String status;

    /**
     * 
     * 菜单图标 
     */
    private String icon;

    /**
     * 
     * 创建时间 
     */
    private Date createTime;

    /**
     * 
     * 更新时间 
     */
    private Date updateTime;

    /**
     * 
     * 备注 
     */
    private String remark;

    /**
     * 
     * 资源唯一业务编码 
     */
    private String code;

    
    /**
     * 创建时间
     */
    private Date createTimeBegin;

    /**
     * 创建时间
     */
    private Date createTimeEnd;

    /**
     * 更新时间
     */
    private Date updateTimeBegin;

    /**
     * 更新时间
     */
    private Date updateTimeEnd;

    public List<ResourcesDto> getSubRights() {
        return subRights;
    }

    public void setSubRights(List<ResourcesDto> subRights) {
        this.subRights = subRights;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     * 子集
     */
    private List<ResourcesDto> subRights;

    private String parent;

    /**
     * 是否选中
     */
    private Boolean checked;
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    
    public Long getPid() {
        return this.pid;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public void setSrcType(Boolean srcType) {
        this.srcType = srcType;
    }
    
    public Boolean getSrcType() {
        return this.srcType;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public Integer getSort() {
        return this.sort;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getIcon() {
        return this.icon;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getRemark() {
        return this.remark;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }


    public Date getCreateTimeBegin() {
        return this.createTimeBegin;
    }
    
    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }    
    
    public Date getCreateTimeEnd() {
        return this.createTimeEnd;
    }
    
    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
    
    public Date getUpdateTimeBegin() {
        return this.updateTimeBegin;
    }
    
    public void setUpdateTimeBegin(Date updateTimeBegin) {
        this.updateTimeBegin = updateTimeBegin;
    }    
    
    public Date getUpdateTimeEnd() {
        return this.updateTimeEnd;
    }
    
    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }
    

}

