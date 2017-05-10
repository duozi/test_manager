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
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.RelationDatabaseEnvironmentService;
import com.xn.interfacetest.dao.RelationDatabaseEnvironmentMapper;
import com.xn.interfacetest.dto.RelationDatabaseEnvironmentDto;
import com.xn.interfacetest.entity.RelationDatabaseEnvironment;
import com.xn.interfacetest.util.CollectionUtils;


/**
 * RelationDatabaseEnvironment Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationDatabaseEnvironmentServiceImpl implements RelationDatabaseEnvironmentService {

    /**
     *  Dao
     */
    @Autowired
    private RelationDatabaseEnvironmentMapper relationDatabaseEnvironmentMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationDatabaseEnvironmentDto get(Object condition)
	{  
        RelationDatabaseEnvironment relationDatabaseEnvironment = relationDatabaseEnvironmentMapper.get(condition);
        RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto = BeanUtils.toBean(relationDatabaseEnvironment,RelationDatabaseEnvironmentDto.class);
	    return relationDatabaseEnvironmentDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationDatabaseEnvironmentDto condition) {
        return relationDatabaseEnvironmentMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationDatabaseEnvironmentDto> list(RelationDatabaseEnvironmentDto condition) {
        List<RelationDatabaseEnvironment> list = relationDatabaseEnvironmentMapper.list(condition);
        List<RelationDatabaseEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationDatabaseEnvironmentDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationDatabaseEnvironmentDto> list(Map<String,Object> condition) {
        List<RelationDatabaseEnvironment> list = relationDatabaseEnvironmentMapper.list(condition);
        List<RelationDatabaseEnvironmentDto> dtoList = CollectionUtils.transform(list, RelationDatabaseEnvironmentDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationDatabaseEnvironmentDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationDatabaseEnvironmentDto save(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto) {
        RelationDatabaseEnvironment relationDatabaseEnvironment = BeanUtils.toBean(relationDatabaseEnvironmentDto,RelationDatabaseEnvironment.class);
        relationDatabaseEnvironmentMapper.save(relationDatabaseEnvironment);
        relationDatabaseEnvironmentDto.setId(relationDatabaseEnvironment.getId());
        return relationDatabaseEnvironmentDto;
    }

    @Override
    public int save(List<RelationDatabaseEnvironmentDto> relationDatabaseEnvironmentDtos) {
        if (relationDatabaseEnvironmentDtos == null || relationDatabaseEnvironmentDtos.isEmpty()) {
            return 0;
        }
        List<RelationDatabaseEnvironment> relationDatabaseEnvironments = CollectionUtils.transform(relationDatabaseEnvironmentDtos, RelationDatabaseEnvironment.class);
        return relationDatabaseEnvironmentMapper.saveBatch(relationDatabaseEnvironments);
    }

    @Override
    public int update(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto) {
        RelationDatabaseEnvironment relationDatabaseEnvironment = BeanUtils.toBean(relationDatabaseEnvironmentDto,RelationDatabaseEnvironment.class);
        return relationDatabaseEnvironmentMapper.update(relationDatabaseEnvironment);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationDatabaseEnvironmentMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationDatabaseEnvironmentDto relationDatabaseEnvironmentDto) {
        RelationDatabaseEnvironment relationDatabaseEnvironment = BeanUtils.toBean(relationDatabaseEnvironmentDto,RelationDatabaseEnvironment.class);
        return relationDatabaseEnvironmentMapper.delete(relationDatabaseEnvironment);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationDatabaseEnvironmentMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationDatabaseEnvironmentDto> relationDatabaseEnvironments) {
        return 0;
    }

}
