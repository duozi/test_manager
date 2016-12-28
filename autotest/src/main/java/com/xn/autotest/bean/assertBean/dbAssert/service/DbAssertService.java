/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.dbAssert.service;

import com.xn.autotest.bean.assertBean.dbAssert.dto.DbAssertDto;
import com.xn.autotest.mybatis.PageResult;

import java.util.List;
import java.util.Map;


/**
 * DbAssert Service
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public interface DbAssertService  {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    DbAssertDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(DbAssertDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DbAssertDto> list(DbAssertDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<DbAssertDto> list(Map<String,Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<DbAssertDto> page(Map<String,Object> condition);
    

    /**
     * 保存
     * 
     * @param dbAssertDto
     * @return 带主键的DTO
     */
    DbAssertDto save(DbAssertDto dbAssertDto);

    /**
     * 批量保存
     * 
     * @param dbAssertDtos
     * @return 带主键的DTO
     */
    int save(List<DbAssertDto> dbAssertDtos);

    /**
     * 更新
     * 
     * @param dbAssertDto
     * @return 操作影响行数
     */
    int update(DbAssertDto dbAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(DbAssert dbAssert)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Integer id);
    
    /**
     * 删除
     * 
     * @param dbAssertDto 主键
     * @return 操作影响行数
     */
    int delete(DbAssertDto dbAssertDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(DbAssertDto dbAssert)
     * @param ids 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Integer> ids);
    
    
    /**
     * 批量删除
     * 
     * @param dbAsserts 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<DbAssertDto> dbAsserts);
}
