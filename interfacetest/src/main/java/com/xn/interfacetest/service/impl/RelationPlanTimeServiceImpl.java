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
import com.xn.interfacetest.dao.RelationPlanTimeMapper;
import com.xn.interfacetest.dto.RelationPlanTimeDto;
import com.xn.interfacetest.entity.RelationPlanTime;
import com.xn.interfacetest.service.RelationPlanTimeService;


/**
 * RelationPlanTime Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationPlanTimeServiceImpl implements RelationPlanTimeService {

    /**
     *  Dao
     */
    @Autowired
    private RelationPlanTimeMapper relationPlanTimeMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationPlanTimeDto get(Object condition)
	{  
        RelationPlanTime relationPlanTime = relationPlanTimeMapper.get(condition);
        RelationPlanTimeDto relationPlanTimeDto = BeanUtils.toBean(relationPlanTime,RelationPlanTimeDto.class);
	    return relationPlanTimeDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationPlanTimeDto condition) {
        return relationPlanTimeMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanTimeDto> list(RelationPlanTimeDto condition) {
        List<RelationPlanTime> list = relationPlanTimeMapper.list(condition);
        List<RelationPlanTimeDto> dtoList = CollectionUtils.transform(list, RelationPlanTimeDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationPlanTimeDto> list(Map<String,Object> condition) {
        List<RelationPlanTime> list = relationPlanTimeMapper.list(condition);
        List<RelationPlanTimeDto> dtoList = CollectionUtils.transform(list, RelationPlanTimeDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationPlanTimeDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationPlanTimeDto save(RelationPlanTimeDto relationPlanTimeDto) {
        RelationPlanTime relationPlanTime = BeanUtils.toBean(relationPlanTimeDto,RelationPlanTime.class);
        relationPlanTimeMapper.save(relationPlanTime);
        relationPlanTimeDto.setId(relationPlanTime.getId());
        return relationPlanTimeDto;
    }

    @Override
    public int save(List<RelationPlanTimeDto> relationPlanTimeDtos) {
        if (relationPlanTimeDtos == null || relationPlanTimeDtos.isEmpty()) {
            return 0;
        }
        List<RelationPlanTime> relationPlanTimes = CollectionUtils.transform(relationPlanTimeDtos, RelationPlanTime.class);
        return relationPlanTimeMapper.saveBatch(relationPlanTimes);
    }

    @Override
    public int update(RelationPlanTimeDto relationPlanTimeDto) {
        RelationPlanTime relationPlanTime = BeanUtils.toBean(relationPlanTimeDto,RelationPlanTime.class);
        return relationPlanTimeMapper.update(relationPlanTime);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationPlanTimeMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationPlanTimeDto relationPlanTimeDto) {
        RelationPlanTime relationPlanTime = BeanUtils.toBean(relationPlanTimeDto,RelationPlanTime.class);
        return relationPlanTimeMapper.delete(relationPlanTime);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationPlanTimeMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationPlanTimeDto> relationPlanTimes) {
        return 0;
    }

}
