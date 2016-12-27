/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.redisOperation.service;

import com.xn.autotest.bean.operation.redisOperation.dto.RedisOperationDto;
import com.xn.autotest.mybatis.PageResult;

import java.util.List;
import java.util.Map;


/**
 * RedisOperation Service
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public interface RedisOperationService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    RedisOperationDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(RedisOperationDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RedisOperationDto> list(RedisOperationDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RedisOperationDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<RedisOperationDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param redisOperationDto
     * @return 带主键的DTO
     */
    RedisOperationDto save(RedisOperationDto redisOperationDto);

    /**
     * 批量保存
     * 
     * @param redisOperationDtos
     * @return 带主键的DTO
     */
    int save(List<RedisOperationDto> redisOperationDtos);

    /**
     * 更新
     * 
     * @param redisOperationDto
     * @return 操作影响行数
     */
    int update(RedisOperationDto redisOperationDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RedisOperation redisOperation)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Integer id);
    
    /**
     * 删除
     * 
     * @param redisOperationDto 主键
     * @return 操作影响行数
     */
    int delete(RedisOperationDto redisOperationDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RedisOperationDto redisOperation)
     * @param ids 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Integer> ids);
    
    
    /**
     * 批量删除
     * 
     * @param redisOperations 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<RedisOperationDto> redisOperations);
}
