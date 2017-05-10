/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.api;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.RelationDatabaseEnvironmentDto;

/**
 * RelationDatabaseEnvironment Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface RelationDatabaseEnvironmentService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    RelationDatabaseEnvironmentDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(RelationDatabaseEnvironmentDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RelationDatabaseEnvironmentDto> list(RelationDatabaseEnvironmentDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RelationDatabaseEnvironmentDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<RelationDatabaseEnvironmentDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param relationDatabaseEnvironment 
     * @return 带主键的DTO
     */
    RelationDatabaseEnvironmentDto save(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto);

    /**
     * 批量保存
     * 
     * @param relationDatabaseEnvironments 
     * @return 带主键的DTO
     */
    int save(List<RelationDatabaseEnvironmentDto> relationDatabaseEnvironmentDtos);

    /**
     * 更新
     * 
     * @param relationDatabaseEnvironment 
     * @return 操作影响行数
     */
    int update(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RelationDatabaseEnvironment relationDatabaseEnvironment)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RelationDatabaseEnvironmentDto relationDatabaseEnvironment)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<RelationDatabaseEnvironmentDto> relationDatabaseEnvironments);
}
