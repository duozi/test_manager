/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestInterfaceDto;

/**
 * TestInterface Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestInterfaceService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestInterfaceDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestInterfaceDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestInterfaceDto> list(TestInterfaceDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestInterfaceDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestInterfaceDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testInterface 
     * @return 带主键的DTO
     */
    TestInterfaceDto save(TestInterfaceDto testInterfaceDto);

    /**
     * 批量保存
     * 
     * @param testInterfaces 
     * @return 带主键的DTO
     */
    int save(List<TestInterfaceDto> testInterfaceDtos);

    /**
     * 更新
     * 
     * @param testInterface 
     * @return 操作影响行数
     */
    int update(TestInterfaceDto testInterfaceDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestInterface testInterface)
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
    int delete(TestInterfaceDto testInterfaceDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestInterfaceDto testInterface)
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
    int deleteBatch(List<TestInterfaceDto> testInterfaces);

    /**
     * 查询接口（含服务信息）
     * @param params
     * @return
     */
    List<TestInterfaceDto> listByParams(Map<String, Object> params);

    /**
     * 根据测试集id查询接口信息
     * @param o
     * @return
     */
    List<TestInterfaceDto> listAllBySuitId(Object o);

    /**
     * 查询接口信息（含所属服务和系统）
     * @return
     */
    List<TestInterfaceDto> listAll();

    /**
     * 查询接口的参数信息
     * @param interfaceId
     * @return
     */
    String getParamsByInterfaceId(String interfaceId);

    /**
     * 通过用例id查询接口信息
     * @param id
     * @return
     */
    TestInterfaceDto getByCaseId(Long id);

    /**
     * 通过多个接口id查询出接口详细信息
     * @param interfaceArray
     * @return
     */
    List<TestInterfaceDto> listWithInfoByIds(String[] interfaceArray);

    /**
     * 通过多个接口id查询出接口
     * @param interfaceIds
     * @return
     */
    List<TestInterfaceDto> getByInterfaceIds(String interfaceIds);
}
