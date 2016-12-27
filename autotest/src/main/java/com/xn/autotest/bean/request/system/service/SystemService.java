/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.system.service;

import com.xn.autotest.bean.request.system.dto.SystemDto;
import com.xn.autotest.mybatis.PageResult;

import java.util.List;
import java.util.Map;

/**
 * System Service
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public interface SystemService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    SystemDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(SystemDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<SystemDto> list(SystemDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<SystemDto> list(Map<String,Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<SystemDto> page(Map<String,Object> condition);
    

    /**
     * 保存
     * 
     * @param system 
     * @return 带主键的DTO
     */
    SystemDto save(SystemDto systemDto);

    /**
     * 批量保存
     * 
     * @param systems 
     * @return 带主键的DTO
     */
    int save(List<SystemDto> systemDtos);

    /**
     * 更新
     * 
     * @param system 
     * @return 操作影响行数
     */
    int update(SystemDto systemDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(System system)
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
    int delete(SystemDto systemDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(SystemDto system)
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
    int deleteBatch(List<SystemDto> systems);
}
