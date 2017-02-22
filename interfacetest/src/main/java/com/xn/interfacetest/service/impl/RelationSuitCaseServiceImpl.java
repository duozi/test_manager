/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.RelationSuitCaseMapper;
import com.xn.interfacetest.dto.RelationSuitCaseDto;
import com.xn.interfacetest.entity.RelationSuitCase;
import com.xn.interfacetest.service.RelationSuitCaseService;

/**
 * RelationSuitCase Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationSuitCaseServiceImpl implements RelationSuitCaseService {

    /**
     *  Dao
     */
    @Autowired
    private RelationSuitCaseMapper relationSuitCaseMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationSuitCaseDto get(Object condition)
	{  
        RelationSuitCase relationSuitCase = relationSuitCaseMapper.get(condition);
        RelationSuitCaseDto relationSuitCaseDto = BeanUtils.toBean(relationSuitCase,RelationSuitCaseDto.class);
	    return relationSuitCaseDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationSuitCaseDto condition) {
        return relationSuitCaseMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationSuitCaseDto> list(RelationSuitCaseDto condition) {
        List<RelationSuitCase> list = relationSuitCaseMapper.list(condition);
        List<RelationSuitCaseDto> dtoList = CollectionUtils.transform(list, RelationSuitCaseDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationSuitCaseDto> list(Map<String,Object> condition) {
        List<RelationSuitCase> list = relationSuitCaseMapper.list(condition);
        List<RelationSuitCaseDto> dtoList = CollectionUtils.transform(list, RelationSuitCaseDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationSuitCaseDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationSuitCaseDto save(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        relationSuitCaseMapper.save(relationSuitCase);
        relationSuitCaseDto.setId(relationSuitCase.getId());
        return relationSuitCaseDto;
    }

    @Override
    public int save(List<RelationSuitCaseDto> relationSuitCaseDtos) {
        if (relationSuitCaseDtos == null || relationSuitCaseDtos.isEmpty()) {
            return 0;
        }
        List<RelationSuitCase> relationSuitCases = CollectionUtils.transform(relationSuitCaseDtos, RelationSuitCase.class);
        return relationSuitCaseMapper.saveBatch(relationSuitCases);
    }

    @Override
    public int update(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        return relationSuitCaseMapper.update(relationSuitCase);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationSuitCaseMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        return relationSuitCaseMapper.delete(relationSuitCase);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationSuitCaseMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationSuitCaseDto> relationSuitCases) {
        return 0;
    }

}
