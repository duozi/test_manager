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
import com.xn.interfacetest.dao.RelationCaseDatabaseMapper;
import com.xn.interfacetest.dto.RelationCaseDatabaseDto;
import com.xn.interfacetest.entity.RelationCaseDatabase;
import com.xn.interfacetest.service.RelationCaseDatabaseService;



/**
 * RelationCaseDatabase Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationCaseDatabaseServiceImpl implements RelationCaseDatabaseService {

    /**
     *  Dao
     */
    @Autowired
    private RelationCaseDatabaseMapper relationCaseDatabaseMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationCaseDatabaseDto get(Object condition)
	{  
        RelationCaseDatabase relationCaseDatabase = relationCaseDatabaseMapper.get(condition);
        RelationCaseDatabaseDto relationCaseDatabaseDto = BeanUtils.toBean(relationCaseDatabase,RelationCaseDatabaseDto.class);
	    return relationCaseDatabaseDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationCaseDatabaseDto condition) {
        return relationCaseDatabaseMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseDatabaseDto> list(RelationCaseDatabaseDto condition) {
        List<RelationCaseDatabase> list = relationCaseDatabaseMapper.list(condition);
        List<RelationCaseDatabaseDto> dtoList = CollectionUtils.transform(list, RelationCaseDatabaseDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseDatabaseDto> list(Map<String,Object> condition) {
        List<RelationCaseDatabase> list = relationCaseDatabaseMapper.list(condition);
        List<RelationCaseDatabaseDto> dtoList = CollectionUtils.transform(list, RelationCaseDatabaseDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationCaseDatabaseDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationCaseDatabaseDto save(RelationCaseDatabaseDto relationCaseDatabaseDto) {
        RelationCaseDatabase relationCaseDatabase = BeanUtils.toBean(relationCaseDatabaseDto,RelationCaseDatabase.class);
        if(null == relationCaseDatabaseDto.getId()){
            relationCaseDatabaseMapper.save(relationCaseDatabase);
        } else {
            relationCaseDatabaseMapper.update(relationCaseDatabase);
        }

        relationCaseDatabaseDto.setId(relationCaseDatabase.getId());
        return relationCaseDatabaseDto;
    }

    @Override
    public int save(List<RelationCaseDatabaseDto> relationCaseDatabaseDtos) {
        if (relationCaseDatabaseDtos == null || relationCaseDatabaseDtos.isEmpty()) {
            return 0;
        }
        List<RelationCaseDatabase> relationCaseDatabases = CollectionUtils.transform(relationCaseDatabaseDtos, RelationCaseDatabase.class);
        return relationCaseDatabaseMapper.saveBatch(relationCaseDatabases);
    }

    @Override
    public int update(RelationCaseDatabaseDto relationCaseDatabaseDto) {
        RelationCaseDatabase relationCaseDatabase = BeanUtils.toBean(relationCaseDatabaseDto,RelationCaseDatabase.class);
        return relationCaseDatabaseMapper.update(relationCaseDatabase);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationCaseDatabaseMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationCaseDatabaseDto relationCaseDatabaseDto) {
        RelationCaseDatabase relationCaseDatabase = BeanUtils.toBean(relationCaseDatabaseDto,RelationCaseDatabase.class);
        return relationCaseDatabaseMapper.delete(relationCaseDatabase);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationCaseDatabaseMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationCaseDatabaseDto> relationCaseDatabases) {
        return 0;
    }

}
