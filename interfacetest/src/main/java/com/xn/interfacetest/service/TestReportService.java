/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestReportDto;


/**
 * TestReport Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestReportService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestReportDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestReportDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestReportDto> list(TestReportDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestReportDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestReportDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testResult 
     * @return 带主键的DTO
     */
    TestReportDto save(TestReportDto testReportDto);

    /**
     * 批量保存
     * 
     * @param testResults 
     * @return 带主键的DTO
     */
    int save(List<TestReportDto> testReportDtos);

    /**
     * 更新
     * 
     * @param testResult 
     * @return 操作影响行数
     */
    int update(TestReportDto testReportDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestReport testResult)
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
    int delete(TestReportDto testReportDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestReportDto testResult)
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
    int deleteBatch(List<TestReportDto> testResults);

    /**
     * 根据条件查询测试报告，查询出：测试计划、测试集、测试环境
     * @param params
     * @return
     */
    List<TestReportDto> selectWithOtherInfo(Map<String, Object> params);

    TestReportDto getWithInfo(long l);
}
