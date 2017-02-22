/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.common.company.service;

import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Department Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface DepartmentService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    DepartmentDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(DepartmentDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DepartmentDto> list(DepartmentDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DepartmentDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<DepartmentDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @return 带主键的DTO
     */
    DepartmentDto save(DepartmentDto departmentDto);

    /**
     * 批量保存
     * 
     * @param departments 
     * @return 带主键的DTO
     */
    int save(List<DepartmentDto> departmentDtos);

    /**
     * 更新
     * 
     * @param department 
     * @return 操作影响行数
     */
    int update(DepartmentDto departmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(Department department)
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
    int delete(DepartmentDto departmentDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(DepartmentDto department)
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
    int deleteBatch(List<DepartmentDto> departments);
}
