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
import com.xn.interfacetest.dao.RelationPlanSuitMapper;
import com.xn.interfacetest.dto.RelationPlanSuitDto;
import com.xn.interfacetest.entity.RelationPlanSuit;
import com.xn.interfacetest.service.RelationPlanSuitService;

/**
 * RelationPlanSuit Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationPlanSuitServiceImpl implements RelationPlanSuitService {

    /**
     *  Dao
     */
    @Autowired
    private RelationPlanSuitMapper relationPlanSuitMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationPlanSuitDto get(Object condition)
	{  
        RelationPlanSuit relationPlanSuit = relationPlanSuitMapper.get(condition);
        RelationPlanSuitDto relationPlanSuitDto = BeanUtils.toBean(relationPlanSuit,RelationPlanSuitDto.class);
	    return relationPlanSuitDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationPlanSuitDto condition) {
        return relationPlanSuitMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanSuitDto> list(RelationPlanSuitDto condition) {
        List<RelationPlanSuit> list = relationPlanSuitMapper.list(condition);
        List<RelationPlanSuitDto> dtoList = CollectionUtils.transform(list, RelationPlanSuitDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanSuitDto> list(Map<String,Object> condition) {
        List<RelationPlanSuit> list = relationPlanSuitMapper.list(condition);
        List<RelationPlanSuitDto> dtoList = CollectionUtils.transform(list, RelationPlanSuitDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationPlanSuitDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationPlanSuitDto save(RelationPlanSuitDto relationPlanSuitDto) {
        RelationPlanSuit relationPlanSuit = BeanUtils.toBean(relationPlanSuitDto,RelationPlanSuit.class);
        relationPlanSuitMapper.save(relationPlanSuit);
        relationPlanSuitDto.setId(relationPlanSuit.getId());
        return relationPlanSuitDto;
    }

    @Override
    public int save(List<RelationPlanSuitDto> relationPlanSuitDtos) {
        if (relationPlanSuitDtos == null || relationPlanSuitDtos.isEmpty()) {
            return 0;
        }
        List<RelationPlanSuit> relationPlanSuits = CollectionUtils.transform(relationPlanSuitDtos, RelationPlanSuit.class);
        return relationPlanSuitMapper.saveBatch(relationPlanSuits);
    }

    @Override
    public int update(RelationPlanSuitDto relationPlanSuitDto) {
        RelationPlanSuit relationPlanSuit = BeanUtils.toBean(relationPlanSuitDto,RelationPlanSuit.class);
        return relationPlanSuitMapper.update(relationPlanSuit);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationPlanSuitMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationPlanSuitDto relationPlanSuitDto) {
        RelationPlanSuit relationPlanSuit = BeanUtils.toBean(relationPlanSuitDto,RelationPlanSuit.class);
        return relationPlanSuitMapper.delete(relationPlanSuit);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationPlanSuitMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationPlanSuitDto> relationPlanSuits) {
        return 0;
    }

}
