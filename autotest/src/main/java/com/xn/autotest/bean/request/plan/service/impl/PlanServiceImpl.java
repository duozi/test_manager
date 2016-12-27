/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.plan.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.plan.entity.Plan;
import com.xn.autotest.bean.request.plan.service.PlanService;
import com.xn.autotest.dao.PlanMapper;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Plan Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public class PlanServiceImpl implements PlanService {

    /**
     *  Dao
     */
    @Autowired
     PlanMapper planMapper;

    @Override
    public PlanDto get(Object condition)  
	{  
        Plan plan = planMapper.get(condition);
        PlanDto planDto = BeanUtils.toBean(plan,PlanDto.class);
	    return planDto;  
	}  

    @Override
    public long count(PlanDto condition) {
        return planMapper.count(condition);
    }

    @Override
    public List<PlanDto> list(PlanDto condition) {
        List<Plan> list = planMapper.list(condition);
        List<PlanDto> dtoList = CollectionUtils.transform(list, PlanDto.class);
        return dtoList;
    }

    @Override
    public List<PlanDto> list() {
        List<Plan> list = planMapper.list(null);
        List<PlanDto> dtoList = CollectionUtils.transform(list, PlanDto.class);
        return dtoList;
    }

    @Override
    public List<PlanDto> list(Map<String,Object> condition) {
        List<Plan> list = planMapper.list(condition);
        List<PlanDto> dtoList = CollectionUtils.transform(list, PlanDto.class);
        return dtoList;
    }

    @Override
    public PageResult<PlanDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PlanDto save(PlanDto planDto) {
        Plan plan = BeanUtils.toBean(planDto,Plan.class);
        planMapper.save(plan);
        planDto.setId(plan.getId());
        return planDto;
    }

    @Override
    public int save(List<PlanDto> planDtos) {
        if (planDtos == null || planDtos.isEmpty()) {
            return 0;
        }
        List<Plan> plans = CollectionUtils.transform(planDtos, Plan.class);
        return planMapper.saveBatch(plans);
    }

    @Override
    public int update(PlanDto planDto) {
        Plan plan = BeanUtils.toBean(planDto,Plan.class);
        return planMapper.update(plan);
    }

    @Override
    public int deleteByPK(Integer id) {
        return planMapper.deleteByPK(id);
    }

    @Override
    public int delete(PlanDto planDto) {
        Plan plan = BeanUtils.toBean(planDto,Plan.class);
        return planMapper.delete(plan);
    }

    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return planMapper.deleteBatchByPK(ids);
    }

    @Override
    public int deleteBatch(List<PlanDto> plans) {
        return 0;
    }

}
