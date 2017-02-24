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
import com.xn.interfacetest.dao.RelationPlanEnvironmentMapper;
import com.xn.interfacetest.dto.RelationPlanEnvironmentDto;
import com.xn.interfacetest.entity.RelationPlanEnvironment;
import com.xn.interfacetest.service.RelationPlanEnvironmentService;




/**
 * RelationPlanEnvironment Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationPlanEnvironmentServiceImpl implements RelationPlanEnvironmentService {

    /**
     *  Dao
     */
    @Autowired
    private RelationPlanEnvironmentMapper relationPlanEnvironmentMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationPlanEnvironmentDto get(Object condition)
	{  
        RelationPlanEnvironment relationPlanEnvironment = relationPlanEnvironmentMapper.get(condition);
        RelationPlanEnvironmentDto relationPlanEnvironmentDto = BeanUtils.toBean(relationPlanEnvironment,RelationPlanEnvironmentDto.class);
	    return relationPlanEnvironmentDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationPlanEnvironmentDto condition) {
        return relationPlanEnvironmentMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanEnvironmentDto> list(RelationPlanEnvironmentDto condition) {
        List<RelationPlanEnvironment> list = relationPlanEnvironmentMapper.list(condition);
        List<RelationPlanEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationPlanEnvironmentDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanEnvironmentDto> list(Map<String,Object> condition) {
        List<RelationPlanEnvironment> list = relationPlanEnvironmentMapper.list(condition);
        List<RelationPlanEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationPlanEnvironmentDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationPlanEnvironmentDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationPlanEnvironmentDto save(RelationPlanEnvironmentDto relationPlanEnvironmentDto) {
        RelationPlanEnvironment relationPlanEnvironment = BeanUtils.toBean(relationPlanEnvironmentDto,RelationPlanEnvironment.class);
        relationPlanEnvironmentMapper.save(relationPlanEnvironment);
        relationPlanEnvironmentDto.setId(relationPlanEnvironment.getId());
        return relationPlanEnvironmentDto;
    }

    @Override
    public int save(List<RelationPlanEnvironmentDto> relationPlanEnvironmentDtos) {
        if (relationPlanEnvironmentDtos == null || relationPlanEnvironmentDtos.isEmpty()) {
            return 0;
        }
        List<RelationPlanEnvironment> relationPlanEnvironments = CollectionUtils.transform(relationPlanEnvironmentDtos, RelationPlanEnvironment.class);
        return relationPlanEnvironmentMapper.saveBatch(relationPlanEnvironments);
    }

    @Override
    public int update(RelationPlanEnvironmentDto relationPlanEnvironmentDto) {
        RelationPlanEnvironment relationPlanEnvironment = BeanUtils.toBean(relationPlanEnvironmentDto,RelationPlanEnvironment.class);
        return relationPlanEnvironmentMapper.update(relationPlanEnvironment);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationPlanEnvironmentMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationPlanEnvironmentDto relationPlanEnvironmentDto) {
        RelationPlanEnvironment relationPlanEnvironment = BeanUtils.toBean(relationPlanEnvironmentDto,RelationPlanEnvironment.class);
        return relationPlanEnvironmentMapper.delete(relationPlanEnvironment);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationPlanEnvironmentMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationPlanEnvironmentDto> relationPlanEnvironments) {
        return 0;
    }

}
