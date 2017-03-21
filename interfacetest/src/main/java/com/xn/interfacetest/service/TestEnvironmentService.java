/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestEnvironmentDto;

/**
 * TestEnvironment Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestEnvironmentService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestEnvironmentDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestEnvironmentDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestEnvironmentDto> list(TestEnvironmentDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestEnvironmentDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestEnvironmentDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testEnvironment 
     * @return 带主键的DTO
     */
    TestEnvironmentDto save(TestEnvironmentDto testEnvironmentDto);

    /**
     * 批量保存
     * 
     * @param testEnvironments 
     * @return 带主键的DTO
     */
    int save(List<TestEnvironmentDto> testEnvironmentDtos);

    /**
     * 更新
     * 
     * @param testEnvironment 
     * @return 操作影响行数
     */
    int update(TestEnvironmentDto testEnvironmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestEnvironment testEnvironment)
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
    int delete(TestEnvironmentDto testEnvironmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestEnvironmentDto testEnvironment)
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
    int deleteBatch(List<TestEnvironmentDto> testEnvironments);

    /**
     * 查询环境（含系统信息）
     * @param params
     * @return
     */
    List<TestEnvironmentDto> listWithSystem(Map<String, Object> params);

    /**
     *查询环境（含系统信息）
     * @param id
     * @return
     */
    TestEnvironmentDto getWithSystem(Long id);

    List<TestEnvironmentDto> getByPlanId(Long id);
}
