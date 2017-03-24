/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestSuitDto;

/**
 * TestSuit Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestSuitService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestSuitDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestSuitDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestSuitDto> list(TestSuitDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestSuitDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestSuitDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testSuit 
     * @return 带主键的DTO
     */
    TestSuitDto save(TestSuitDto testSuitDto);

    /**
     * 批量保存
     * 
     * @param testSuits 
     * @return 带主键的DTO
     */
    int save(List<TestSuitDto> testSuitDtos);

    /**
     * 更新
     * 
     * @param testSuit 
     * @return 操作影响行数
     */
    int update(TestSuitDto testSuitDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestSuit testSuit)
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
    int delete(TestSuitDto testSuitDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestSuitDto testSuit)
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
    int deleteBatch(List<TestSuitDto> testSuits);

    /**
     * 查询测试集（含接口信息和系统信息）
     * @param params
     * @return
     */
    List<TestSuitDto> listWithSystemAndInterface(Map<String, Object> params);

    /**
     * 查询含有该测试用例的测试集
     * @param l
     * @return
     */
    List<TestSuitDto> getSuitByCaseId(Long id);

    /**
     * 根据测试计划查询测试集信息
     * @param id
     * @return
     */
    List<TestSuitDto> getByPlanId(Long id);
}
