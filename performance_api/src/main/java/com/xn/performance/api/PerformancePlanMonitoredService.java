/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.api;

import com.xn.performance.dto.PerformancePlanMonitoredDto;
import com.xn.performance.mybatis.PageResult;

import java.util.List;
import java.util.Map;

/**
 * PerformancePlanMonitored Service
 * 
 * @author zhouxi
 * @date 2017-03-01
 */
public interface PerformancePlanMonitoredService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    PerformancePlanMonitoredDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(PerformancePlanMonitoredDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformancePlanMonitoredDto> list(PerformancePlanMonitoredDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformancePlanMonitoredDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<PerformancePlanMonitoredDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param performancePlanMonitored 
     * @return 带主键的DTO
     */
    PerformancePlanMonitoredDto save(PerformancePlanMonitoredDto performancePlanMonitoredDto);

    /**
     * 批量保存
     * 
     * @param performancePlanMonitoreds 
     * @return 带主键的DTO
     */
    int save(List<PerformancePlanMonitoredDto> performancePlanMonitoredDtos);

    /**
     * 更新
     * 
     * @param performancePlanMonitored 
     * @return 操作影响行数
     */
    int update(PerformancePlanMonitoredDto performancePlanMonitoredDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformancePlanMonitored performancePlanMonitored)
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
    int delete(PerformancePlanMonitoredDto performancePlanMonitoredDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformancePlanMonitoredDto performancePlanMonitored)
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
    int deleteBatch(List<PerformancePlanMonitoredDto> performancePlanMonitoreds);
}
