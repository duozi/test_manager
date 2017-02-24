/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;


import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.DataAssertDto;

import java.util.List;
import java.util.Map;

/**
 * DataAssert Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface DataAssertService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    DataAssertDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(DataAssertDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DataAssertDto> list(DataAssertDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DataAssertDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<DataAssertDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param dataAssertDto
     * @return 带主键的DTO
     */
    DataAssertDto save(DataAssertDto dataAssertDto);

    /**
     * 批量保存
     * 
     * @param dataAssertDtos
     * @return 带主键的DTO
     */
    int save(List<DataAssertDto> dataAssertDtos);

    /**
     * 更新
     * 
     * @param dataAssertDto
     * @return 操作影响行数
     */
    int update(DataAssertDto dataAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(DataAssert dataAssert)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除
     * 
     * @param dataAssertDto 主键
     * @return 操作影响行数
     */
    int delete(DataAssertDto dataAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(DataAssertDto dataAssert)
     * @param ids 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除
     * 
     * @param dataAsserts 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<DataAssertDto> dataAsserts);
}
