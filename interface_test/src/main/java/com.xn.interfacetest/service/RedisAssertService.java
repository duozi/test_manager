/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.RedisAssertDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * RedisAssert Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface RedisAssertService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    RedisAssertDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(RedisAssertDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RedisAssertDto> list(RedisAssertDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RedisAssertDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<RedisAssertDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param redisAssert 
     * @return 带主键的DTO
     */
    RedisAssertDto save(RedisAssertDto redisAssertDto);

    /**
     * 批量保存
     * 
     * @param redisAsserts 
     * @return 带主键的DTO
     */
    int save(List<RedisAssertDto> redisAssertDtos);

    /**
     * 更新
     * 
     * @param redisAssert 
     * @return 操作影响行数
     */
    int update(RedisAssertDto redisAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RedisAssert redisAssert)
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
    int delete(RedisAssertDto redisAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RedisAssertDto redisAssert)
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
    int deleteBatch(List<RedisAssertDto> redisAsserts);
}
