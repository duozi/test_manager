/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.api;

import com.xn.performance.dto.PerformancePlanDto;
import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.mybatis.PageResult;

import java.util.List;
import java.util.Map;

/**
 * PerformancePlan Service
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public interface PerformancePlanService {

    /**
     * 查询计划记录
     * 主键：id
     * @param condition 主键/Map/查询对象
     * @return
     */
    List<PerformancePlanShowDto>  show(Map<String,Object> condition);

    /**
     * 查询计划记录
     * 主键：id
     * @param condition 主键/Map/查询对象
     * @return
     */
    List<PerformancePlanShowDto>  resultList(Map<String,Object> condition);
    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    PerformancePlanDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(PerformancePlanDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformancePlanDto> list(PerformancePlanDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformancePlanDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<PerformancePlanDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param performancePlan 
     * @return 带主键的DTO
     */
    PerformancePlanDto save(PerformancePlanDto performancePlanDto);

    /**
     * 批量保存
     * 
     * @param performancePlans 
     * @return 带主键的DTO
     */
    int save(List<PerformancePlanDto> performancePlanDtos);

    /**
     * 更新
     * 
     * @param performancePlan 
     * @return 操作影响行数
     */
    int update(PerformancePlanDto performancePlanDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformancePlan performancePlan)
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
    int delete(PerformancePlanDto performancePlanDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformancePlanDto performancePlan)
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
    int deleteBatch(List<PerformancePlanDto> performancePlans);


}
