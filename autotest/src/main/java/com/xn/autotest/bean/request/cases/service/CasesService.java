/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.cases.service;

import com.xn.autotest.bean.request.cases.dto.CasesDto;
import com.xn.autotest.mybatis.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Cases Service
 * 
 * @author xn056839
 * @date 2016-12-22
 */
public interface CasesService {

    /**
     * 查询单个记录
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 
     */
    CasesDto get(Object condition);

    /**
     * 统计数量
     * 
     * @param condition 查询条件对象
     * @return 统计数量
     */
    long count(CasesDto condition);

    /**
     * 根据组合条件查询
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<CasesDto> list(CasesDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    List<CasesDto> list(Map<String,Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  
     * 
     * @param condition 查询对象
     * @return 集合,如果不存在,返回Empty List
     */
    PageResult<CasesDto> page(Map<String,Object> condition);
    

    /**
     * 保存
     * 
     * @param cases 
     * @return 带主键的DTO
     */
    CasesDto save(CasesDto casesDto);

    /**
     * 批量保存
     * 
     * @param casess 
     * @return 带主键的DTO
     */
    int save(List<CasesDto> casesDtos);

    /**
     * 更新
     * 
     * @param cases 
     * @return 操作影响行数
     */
    int update(CasesDto casesDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(Cases cases)
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
    int delete(CasesDto casesDto);
    
    /**
     * 根据主键删除
     * 不建议，建议使用delete(CasesDto cases)
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
    int deleteBatch(List<CasesDto> casess);
}
