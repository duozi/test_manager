/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.api;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestRedisConfigDto;


/**
 * TestRedisConfig Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestRedisConfigService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestRedisConfigDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestRedisConfigDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestRedisConfigDto> list(TestRedisConfigDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestRedisConfigDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestRedisConfigDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testRedisConfig 
     * @return 带主键的DTO
     */
    TestRedisConfigDto save(TestRedisConfigDto testRedisConfigDto);

    /**
     * 批量保存
     * 
     * @param testRedisConfigs 
     * @return 带主键的DTO
     */
    int save(List<TestRedisConfigDto> testRedisConfigDtos);

    /**
     * 更新
     * 
     * @param testRedisConfig 
     * @return 操作影响行数
     */
    int update(TestRedisConfigDto testRedisConfigDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestRedisConfig testRedisConfig)
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
    int delete(TestRedisConfigDto testRedisConfigDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestRedisConfigDto testRedisConfig)
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
    int deleteBatch(List<TestRedisConfigDto> testRedisConfigs);

    /**
     * 通过环境id和redis名称唯一指定redis信息
     * @param redisName
     * @param environmentId
     * @return
     */
    TestRedisConfigDto getByRedisNameAndEnvironmentId(String redisName, Long environmentId);
}
