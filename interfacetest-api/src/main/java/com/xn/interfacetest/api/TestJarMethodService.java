/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.api;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.TestJarMethodDto;

/**
 * TestJarMethod Service
 * 
 * @author Carol
 * \
 * @date 2017-05-05
 */
public interface TestJarMethodService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    TestJarMethodDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(TestJarMethodDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestJarMethodDto> list(TestJarMethodDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<TestJarMethodDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<TestJarMethodDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param testJarMethod 
     * @return 带主键的DTO
     */
    TestJarMethodDto save(TestJarMethodDto testJarMethodDto);

    /**
     * 批量保存
     * 
     * @param testJarMethods 
     * @return 带主键的DTO
     */
    int save(List<TestJarMethodDto> testJarMethodDtos);

    /**
     * 更新
     * 
     * @param testJarMethod 
     * @return 操作影响行数
     */
    int update(TestJarMethodDto testJarMethodDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestJarMethod testJarMethod)
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
    int delete(TestJarMethodDto testJarMethodDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(TestJarMethodDto testJarMethod)
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
    int deleteBatch(List<TestJarMethodDto> testJarMethods);

    TestJarMethodDto getByMethodNameAndInterfaceId(String methodName,Long intefaceId);

    List<TestJarMethodDto> getByInterfaceId(Long interfaceId);
}
