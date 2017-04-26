/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.api;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestSystemDto;


/**
 * TestSystem Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestSystemService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestSystemDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestSystemDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestSystemDto> list(TestSystemDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestSystemDto> list(Map<String, Object> condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页
     *
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestSystemDto> listByCompany(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestSystemDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testSystemDto
     * @return 带主键的DTO
     */
    TestSystemDto save(TestSystemDto testSystemDto);

    /**
     * 批量保存
     * 
     * @param testSystemDtos
     * @return 带主键的DTO
     */
    int save(List<TestSystemDto> testSystemDtos);

    /**
     * 更新
     * 
     * @param testSystemDto
     * @return 操作影响行数
     */
    int update(TestSystemDto testSystemDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestSystem testSystem)
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
    int delete(TestSystemDto testSystemDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestSystemDto testSystem)
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
    int deleteBatch(List<TestSystemDto> testSystems);

    TestSystemDto getWithCompanyInfo(Long systemId);

    /**
     * 根据多个系统id查询系统信息
     * @param systemIds
     * @return
     */
    List<TestSystemDto> getWithCompanyInfoBySystems(String systemIds);
}
