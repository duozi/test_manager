/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import com.xn.common.entity.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.RelationPlanResultMapper;
import com.xn.interfacetest.dto.RelationPlanResultDto;
import com.xn.interfacetest.entity.RelationPlanResult;
import com.xn.interfacetest.service.RelationPlanResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * RelationPlanResult Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationPlanResultServiceImpl implements RelationPlanResultService {

    /**
     *  Dao
     */
    @Autowired
    private RelationPlanResultMapper relationPlanResultMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationPlanResultDto get(Object condition)
	{  
        RelationPlanResult relationPlanResult = relationPlanResultMapper.get(condition);
        RelationPlanResultDto relationPlanResultDto = BeanUtils.toBean(relationPlanResult,RelationPlanResultDto.class);
	    return relationPlanResultDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationPlanResultDto condition) {
        return relationPlanResultMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanResultDto> list(RelationPlanResultDto condition) {
        List<RelationPlanResult> list = relationPlanResultMapper.list(condition);
        List<RelationPlanResultDto> dtoList = CollectionUtils.transform(list, RelationPlanResultDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanResultDto> list(Map<String,Object> condition) {
        List<RelationPlanResult> list = relationPlanResultMapper.list(condition);
        List<RelationPlanResultDto> dtoList = CollectionUtils.transform(list, RelationPlanResultDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationPlanResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationPlanResultDto save(RelationPlanResultDto relationPlanResultDto) {
        RelationPlanResult relationPlanResult = BeanUtils.toBean(relationPlanResultDto,RelationPlanResult.class);
        relationPlanResultMapper.save(relationPlanResult);
        relationPlanResultDto.setId(relationPlanResult.getId());
        return relationPlanResultDto;
    }

    @Override
    public int save(List<RelationPlanResultDto> relationPlanResultDtos) {
        if (relationPlanResultDtos == null || relationPlanResultDtos.isEmpty()) {
            return 0;
        }
        List<RelationPlanResult> relationPlanResults = CollectionUtils.transform(relationPlanResultDtos, RelationPlanResult.class);
        return relationPlanResultMapper.saveBatch(relationPlanResults);
    }

    @Override
    public int update(RelationPlanResultDto relationPlanResultDto) {
        RelationPlanResult relationPlanResult = BeanUtils.toBean(relationPlanResultDto,RelationPlanResult.class);
        return relationPlanResultMapper.update(relationPlanResult);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationPlanResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationPlanResultDto relationPlanResultDto) {
        RelationPlanResult relationPlanResult = BeanUtils.toBean(relationPlanResultDto,RelationPlanResult.class);
        return relationPlanResultMapper.delete(relationPlanResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationPlanResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationPlanResultDto> relationPlanResults) {
        return 0;
    }

}
