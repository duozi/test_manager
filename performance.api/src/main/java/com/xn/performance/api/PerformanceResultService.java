/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.api;

import java.util.List;
import java.util.Map;

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.mybatis.PageResult;

import com.xn.performance.dto.PerformanceResultDto;

/**
 * PerformanceResult Service
 *
 * @author zhouxi
 * @date 2017-02-21
 */
public interface PerformanceResultService {

    /**
     * 查询单个记录
     * 主键：id
     *
     * @param condition 主键/Map/查询对象
     * @return
     */
    PerformanceResultDto get(Object condition);

    /**
     * 统计数量
     *
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(PerformanceResultDto condition);

    /**
     * 根据组合条件查询
     *
     * @param condition 查询对象
     * @return 集合, 如果不存在, 返回Empty List
     */
    List<PerformanceResultDto> list(PerformanceResultDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页
     *
     * @param condition 查询对象
     * @return 集合, 如果不存在, 返回Empty List
     */
    List<PerformanceResultDto> list(Map<String, Object> condition);

    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page
     *
     * @param condition 查询对象
     * @return 集合, 如果不存在, 返回Empty List
     */
    PageResult<PerformanceResultDto> page(Map<String, Object> condition);


    /**
     * 保存
     *
     * @param performanceResult
     * @return 带主键的DTO
     */
    PerformanceResultDto save(PerformanceResultDto performanceResultDto);

    /**
     * 批量保存
     *
     * @param performanceResults
     * @return 带主键的DTO
     */
    int save(List<PerformanceResultDto> performanceResultDtos);

    /**
     * 更新
     *
     * @param performanceResult
     * @return 操作影响行数
     */
    int update(PerformanceResultDto performanceResultDto);

    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformanceResult performanceResult)
     *
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
    int delete(PerformanceResultDto performanceResultDto);

    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformanceResultDto performanceResult)
     *
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
    int deleteBatch(List<PerformanceResultDto> performanceResults);

    /**
     * 获得为执行的立即执行的任务
     * 主键：id
     *
     * @param condition 主键/Map/查询对象
     * @return
     */
    List<PerformancePlanShowDto> getNowTask(PerformanceResultDto performanceResultDto);

    List<PerformancePlanShowDto> getScheduleTask(PerformanceResultDto performanceResultDto);

    PerformancePlanShowDto getShow(PerformanceResultDto performanceResultDto);

    List<String> listExecutePerson();
}
