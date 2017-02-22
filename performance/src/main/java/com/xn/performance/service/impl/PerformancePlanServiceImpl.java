/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.dao.PerformancePlanMapper;
import com.xn.performance.dto.PerformancePlanDto;
import com.xn.performance.entity.PerformancePlan;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.service.PerformancePlanService;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * PerformancePlan Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformancePlanServiceImpl implements PerformancePlanService {

    /**
     *  Dao
     */
    @Autowired
    private PerformancePlanMapper performancePlanMapper;

    @Override
    public PerformancePlanDto get(Object condition)  
	{  
        PerformancePlan performancePlan = performancePlanMapper.get(condition);
        PerformancePlanDto performancePlanDto = BeanUtils.toBean(performancePlan,PerformancePlanDto.class);
	    return performancePlanDto;  
	}  

    @Override
    public long count(PerformancePlanDto condition) {
        return performancePlanMapper.count(condition);
    }

    @Override
    public List<PerformancePlanDto> list(PerformancePlanDto condition) {
        List<PerformancePlan> list = performancePlanMapper.list(condition);
        List<PerformancePlanDto> dtoList = CollectionUtils.transform(list, PerformancePlanDto.class);
        return dtoList;
    }

    @Override
    public List<PerformancePlanDto> list(Map<String,Object> condition) {
        List<PerformancePlan> list = performancePlanMapper.list(condition);
        List<PerformancePlanDto> dtoList = CollectionUtils.transform(list, PerformancePlanDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformancePlanDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformancePlanDto save(PerformancePlanDto performancePlanDto) {
        PerformancePlan performancePlan = BeanUtils.toBean(performancePlanDto,PerformancePlan.class);
        performancePlanMapper.save(performancePlan);
        performancePlanDto.setId(performancePlan.getId());
        return performancePlanDto;
    }

    @Override
    public int save(List<PerformancePlanDto> performancePlanDtos) {
        if (performancePlanDtos == null || performancePlanDtos.isEmpty()) {
            return 0;
        }
        List<PerformancePlan> performancePlans = CollectionUtils.transform(performancePlanDtos, PerformancePlan.class);
        return performancePlanMapper.saveBatch(performancePlans);
    }

    @Override
    public int update(PerformancePlanDto performancePlanDto) {
        PerformancePlan performancePlan = BeanUtils.toBean(performancePlanDto,PerformancePlan.class);
        return performancePlanMapper.update(performancePlan);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performancePlanMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformancePlanDto performancePlanDto) {
        PerformancePlan performancePlan = BeanUtils.toBean(performancePlanDto,PerformancePlan.class);
        return performancePlanMapper.delete(performancePlan);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performancePlanMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformancePlanDto> performancePlans) {
        return 0;
    }

}
