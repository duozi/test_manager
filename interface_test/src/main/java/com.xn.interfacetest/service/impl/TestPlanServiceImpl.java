/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import com.xn.common.entity.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestPlanMapper;
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.entity.TestPlan;
import com.xn.interfacetest.service.TestPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



/**
 * TestPlan Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestPlanServiceImpl implements TestPlanService {

    /**
     *  Dao
     */
    @Autowired
    private TestPlanMapper testPlanMapper;

    @Override
    @Transactional(readOnly = true)
    public TestPlanDto get(Object condition)
	{  
        TestPlan testPlan = testPlanMapper.get(condition);
        TestPlanDto testPlanDto = BeanUtils.toBean(testPlan,TestPlanDto.class);
	    return testPlanDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestPlanDto condition) {
        return testPlanMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestPlanDto> list(TestPlanDto condition) {
        List<TestPlan> list = testPlanMapper.list(condition);
        List<TestPlanDto> dtoList = CollectionUtils.transform(list, TestPlanDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestPlanDto> list(Map<String,Object> condition) {
        List<TestPlan> list = testPlanMapper.list(condition);
        List<TestPlanDto> dtoList = CollectionUtils.transform(list, TestPlanDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestPlanDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestPlanDto save(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        testPlanMapper.save(testPlan);
        testPlanDto.setId(testPlan.getId());
        return testPlanDto;
    }

    @Override
    public int save(List<TestPlanDto> testPlanDtos) {
        if (testPlanDtos == null || testPlanDtos.isEmpty()) {
            return 0;
        }
        List<TestPlan> testPlans = CollectionUtils.transform(testPlanDtos, TestPlan.class);
        return testPlanMapper.saveBatch(testPlans);
    }

    @Override
    public int update(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        return testPlanMapper.update(testPlan);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testPlanMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        return testPlanMapper.delete(testPlan);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testPlanMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestPlanDto> testPlans) {
        return 0;
    }

}
