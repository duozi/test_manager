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
import com.xn.interfacetest.dao.RelationServiceEnvironmentMapper;
import com.xn.interfacetest.dto.RelationServiceEnvironmentDto;
import com.xn.interfacetest.entity.RelationServiceEnvironment;
import com.xn.interfacetest.service.RelationServiceEnvironmentService;



/**
 * RelationServiceEnvironment Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationServiceEnvironmentServiceImpl implements RelationServiceEnvironmentService {

    /**
     *  Dao
     */
    @Autowired
    private RelationServiceEnvironmentMapper relationServiceEnvironmentMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationServiceEnvironmentDto get(Object condition)
	{  
        RelationServiceEnvironment relationServiceEnvironment = relationServiceEnvironmentMapper.get(condition);
        RelationServiceEnvironmentDto relationServiceEnvironmentDto = BeanUtils.toBean(relationServiceEnvironment,RelationServiceEnvironmentDto.class);
	    return relationServiceEnvironmentDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationServiceEnvironmentDto condition) {
        return relationServiceEnvironmentMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationServiceEnvironmentDto> list(RelationServiceEnvironmentDto condition) {
        List<RelationServiceEnvironment> list = relationServiceEnvironmentMapper.list(condition);
        List<RelationServiceEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationServiceEnvironmentDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationServiceEnvironmentDto> list(Map<String,Object> condition) {
        List<RelationServiceEnvironment> list = relationServiceEnvironmentMapper.list(condition);
        List<RelationServiceEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationServiceEnvironmentDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationServiceEnvironmentDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationServiceEnvironmentDto save(RelationServiceEnvironmentDto relationServiceEnvironmentDto) {
        RelationServiceEnvironment relationServiceEnvironment = BeanUtils.toBean(relationServiceEnvironmentDto,RelationServiceEnvironment.class);
        relationServiceEnvironmentMapper.save(relationServiceEnvironment);
        relationServiceEnvironmentDto.setId(relationServiceEnvironment.getId());
        return relationServiceEnvironmentDto;
    }

    @Override
    public int save(List<RelationServiceEnvironmentDto> relationServiceEnvironmentDtos) {
        if (relationServiceEnvironmentDtos == null || relationServiceEnvironmentDtos.isEmpty()) {
            return 0;
        }
        List<RelationServiceEnvironment> relationServiceEnvironments = CollectionUtils.transform(relationServiceEnvironmentDtos, RelationServiceEnvironment.class);
        return relationServiceEnvironmentMapper.saveBatch(relationServiceEnvironments);
    }

    @Override
    public int update(RelationServiceEnvironmentDto relationServiceEnvironmentDto) {
        RelationServiceEnvironment relationServiceEnvironment = BeanUtils.toBean(relationServiceEnvironmentDto,RelationServiceEnvironment.class);
        return relationServiceEnvironmentMapper.update(relationServiceEnvironment);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationServiceEnvironmentMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationServiceEnvironmentDto relationServiceEnvironmentDto) {
        RelationServiceEnvironment relationServiceEnvironment = BeanUtils.toBean(relationServiceEnvironmentDto,RelationServiceEnvironment.class);
        return relationServiceEnvironmentMapper.delete(relationServiceEnvironment);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationServiceEnvironmentMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationServiceEnvironmentDto> relationServiceEnvironments) {
        return 0;
    }

}
