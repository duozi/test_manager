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
import com.xn.interfacetest.api.RelationCaseRedisService;
import com.xn.interfacetest.dao.RelationCaseRedisMapper;
import com.xn.interfacetest.dto.RelationCaseRedisDto;
import com.xn.interfacetest.entity.RelationCaseRedis;
import com.xn.interfacetest.util.CollectionUtils;


/**
 * RelationCaseRedis Service实现
 * 
 * @author Carol
 * @date 2017-03-02
 */
@Service
@Transactional
public class RelationCaseRedisServiceImpl implements RelationCaseRedisService {

    /**
     *  Dao
     */
    @Autowired
    private RelationCaseRedisMapper relationCaseRedisMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationCaseRedisDto get(Object condition)
	{  
        RelationCaseRedis relationCaseRedis = relationCaseRedisMapper.get(condition);
        RelationCaseRedisDto relationCaseRedisDto = BeanUtils.toBean(relationCaseRedis,RelationCaseRedisDto.class);
	    return relationCaseRedisDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationCaseRedisDto condition) {
        return relationCaseRedisMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseRedisDto> list(RelationCaseRedisDto condition) {
        List<RelationCaseRedis> list = relationCaseRedisMapper.list(condition);
        List<RelationCaseRedisDto> dtoList = CollectionUtils.transform(list, RelationCaseRedisDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseRedisDto> list(Map<String,Object> condition) {
        List<RelationCaseRedis> list = relationCaseRedisMapper.list(condition);
        List<RelationCaseRedisDto> dtoList = CollectionUtils.transform(list, RelationCaseRedisDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationCaseRedisDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationCaseRedisDto save(RelationCaseRedisDto relationCaseRedisDto) {
        RelationCaseRedis relationCaseRedis = BeanUtils.toBean(relationCaseRedisDto,RelationCaseRedis.class);
        if(null == relationCaseRedisDto.getId()){
            relationCaseRedisMapper.save(relationCaseRedis);
        } else {
            relationCaseRedisMapper.update(relationCaseRedis);
        }

        relationCaseRedisDto.setId(relationCaseRedis.getId());
        return relationCaseRedisDto;
    }

    @Override
    public int save(List<RelationCaseRedisDto> relationCaseRedisDtos) {
        if (relationCaseRedisDtos == null || relationCaseRedisDtos.isEmpty()) {
            return 0;
        }
        List<RelationCaseRedis> relationCaseRediss = CollectionUtils.transform(relationCaseRedisDtos, RelationCaseRedis.class);
        return relationCaseRedisMapper.saveBatch(relationCaseRediss);
    }

    @Override
    public int update(RelationCaseRedisDto relationCaseRedisDto) {
        RelationCaseRedis relationCaseRedis = BeanUtils.toBean(relationCaseRedisDto,RelationCaseRedis.class);
        return relationCaseRedisMapper.update(relationCaseRedis);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationCaseRedisMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationCaseRedisDto relationCaseRedisDto) {
        RelationCaseRedis relationCaseRedis = BeanUtils.toBean(relationCaseRedisDto,RelationCaseRedis.class);
        return relationCaseRedisMapper.delete(relationCaseRedis);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationCaseRedisMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationCaseRedisDto> relationCaseRediss) {
        return 0;
    }

    @Override
    public List<RelationCaseRedisDto> getByCaseIdAndOperateType(Long caseId, int operateType) {
        List<RelationCaseRedis> list = relationCaseRedisMapper.getByCaseIdAndOperateType(caseId,operateType);
        List<RelationCaseRedisDto> dtoList = CollectionUtils.transform(list, RelationCaseRedisDto.class);
        return dtoList;
    }

}
