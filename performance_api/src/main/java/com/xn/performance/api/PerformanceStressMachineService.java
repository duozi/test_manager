/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.api;

import com.xn.performance.dto.PerformanceStressMachineDto;
import com.xn.performance.mybatis.PageResult;

import java.util.List;
import java.util.Map;


/**
 * PerformanceStressMachine Service
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
public interface PerformanceStressMachineService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    PerformanceStressMachineDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(PerformanceStressMachineDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformanceStressMachineDto> list(PerformanceStressMachineDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<PerformanceStressMachineDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<PerformanceStressMachineDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param performanceStressMachine 
     * @return 带主键的DTO
     */
    PerformanceStressMachineDto save(PerformanceStressMachineDto performanceStressMachineDto);

    /**
     * 批量保存
     * 
     * @param performanceStressMachines 
     * @return 带主键的DTO
     */
    int save(List<PerformanceStressMachineDto> performanceStressMachineDtos);

    /**
     * 更新
     * 
     * @param performanceStressMachine 
     * @return 操作影响行数
     */
    int update(PerformanceStressMachineDto performanceStressMachineDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformanceStressMachine performanceStressMachine)
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
    int delete(PerformanceStressMachineDto performanceStressMachineDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(PerformanceStressMachineDto performanceStressMachine)
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
    int deleteBatch(List<PerformanceStressMachineDto> performanceStressMachines);

    String sayHello(String name);

    boolean testLink(String ip,String username,String password,int port);

}
