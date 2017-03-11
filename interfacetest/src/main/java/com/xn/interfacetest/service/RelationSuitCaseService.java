/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dto.RelationSuitCaseDto;
import com.xn.interfacetest.dto.TestInterfaceDto;

import javax.servlet.http.HttpServletRequest;


/**
 * RelationSuitCase Service
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface RelationSuitCaseService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    RelationSuitCaseDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(RelationSuitCaseDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RelationSuitCaseDto> list(RelationSuitCaseDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<RelationSuitCaseDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<RelationSuitCaseDto> page(Map<String, Object> condition);
    

    /**
     * 保存
     * 
     * @param relationSuitCase 
     * @return 带主键的DTO
     */
    RelationSuitCaseDto save(RelationSuitCaseDto relationSuitCaseDto);

    /**
     * 批量保存
     * 
     * @param relationSuitCases 
     * @return 带主键的DTO
     */
    int save(List<RelationSuitCaseDto> relationSuitCaseDtos);

    /**
     * 更新
     * 
     * @param relationSuitCase 
     * @return 操作影响行数
     */
    int update(RelationSuitCaseDto relationSuitCaseDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RelationSuitCase relationSuitCase)
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
    int delete(RelationSuitCaseDto relationSuitCaseDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(RelationSuitCaseDto relationSuitCase)
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
    int deleteBatch(List<RelationSuitCaseDto> relationSuitCases);

    /**
     * 查询关联的接口
     * @param paramsMap
     * @return
     */
    List<TestInterfaceDto> listGroupByInterface(Map<String, Object> paramsMap);

    void saveRelation(Long interfaceId, Long suitId, String caseIds);
}
