/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.http.service;

import com.xn.autotest.bean.properties.http.dto.HttpServicePropertiesDto;
import com.xn.autotest.mybatis.PageResult;

import java.util.List;
import java.util.Map;

/**
 * HttpServiceProperties Service
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public interface HttpServicePropertiesService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    HttpServicePropertiesDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(HttpServicePropertiesDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<HttpServicePropertiesDto> list(HttpServicePropertiesDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<HttpServicePropertiesDto> list(Map<String,Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<HttpServicePropertiesDto> page(Map<String,Object> condition);
    

    /**
     * 保存
     * 
     * @param httpServiceProperties 
     * @return 带主键的DTO
     */
    HttpServicePropertiesDto save(HttpServicePropertiesDto httpServicePropertiesDto);

    /**
     * 批量保存
     * 
     * @param httpServicePropertiess 
     * @return 带主键的DTO
     */
    int save(List<HttpServicePropertiesDto> httpServicePropertiesDtos);

    /**
     * 更新
     * 
     * @param httpServiceProperties 
     * @return 操作影响行数
     */
    int update(HttpServicePropertiesDto httpServicePropertiesDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(HttpServiceProperties httpServiceProperties)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Integer id);
    
    /**
     * 删除
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(HttpServicePropertiesDto httpServicePropertiesDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(HttpServicePropertiesDto httpServiceProperties)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Integer> ids);
    
    
    /**
     * 批量删除
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<HttpServicePropertiesDto> httpServicePropertiess);
}
