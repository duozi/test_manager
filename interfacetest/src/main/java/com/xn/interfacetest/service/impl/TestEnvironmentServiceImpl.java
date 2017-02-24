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
import com.xn.interfacetest.dao.TestEnvironmentMapper;
import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.entity.TestEnvironment;
import com.xn.interfacetest.service.TestEnvironmentService;

/**
 * TestEnvironment Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestEnvironmentServiceImpl implements TestEnvironmentService {

    /**
     *  Dao
     */
    @Autowired
    private TestEnvironmentMapper testEnvironmentMapper;

    @Override
    @Transactional(readOnly = true)
    public TestEnvironmentDto get(Object condition)
	{  
        TestEnvironment testEnvironment = testEnvironmentMapper.get(condition);
        TestEnvironmentDto testEnvironmentDto = BeanUtils.toBean(testEnvironment,TestEnvironmentDto.class);
	    return testEnvironmentDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestEnvironmentDto condition) {
        return testEnvironmentMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestEnvironmentDto> list(TestEnvironmentDto condition) {
        List<TestEnvironment> list = testEnvironmentMapper.list(condition);
        List<TestEnvironmentDto> dtoList = CollectionUtils.transform(list, TestEnvironmentDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestEnvironmentDto> list(Map<String,Object> condition) {
        List<TestEnvironment> list = testEnvironmentMapper.list(condition);
        List<TestEnvironmentDto> dtoList = CollectionUtils.transform(list, TestEnvironmentDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestEnvironmentDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestEnvironmentDto save(TestEnvironmentDto testEnvironmentDto) {
        TestEnvironment testEnvironment = BeanUtils.toBean(testEnvironmentDto,TestEnvironment.class);
        testEnvironmentMapper.save(testEnvironment);
        testEnvironmentDto.setId(testEnvironment.getId());
        return testEnvironmentDto;
    }

    @Override
    public int save(List<TestEnvironmentDto> testEnvironmentDtos) {
        if (testEnvironmentDtos == null || testEnvironmentDtos.isEmpty()) {
            return 0;
        }
        List<TestEnvironment> testEnvironments = CollectionUtils.transform(testEnvironmentDtos, TestEnvironment.class);
        return testEnvironmentMapper.saveBatch(testEnvironments);
    }

    @Override
    public int update(TestEnvironmentDto testEnvironmentDto) {
        TestEnvironment testEnvironment = BeanUtils.toBean(testEnvironmentDto,TestEnvironment.class);
        return testEnvironmentMapper.update(testEnvironment);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testEnvironmentMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestEnvironmentDto testEnvironmentDto) {
        TestEnvironment testEnvironment = BeanUtils.toBean(testEnvironmentDto,TestEnvironment.class);
        return testEnvironmentMapper.delete(testEnvironment);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testEnvironmentMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestEnvironmentDto> testEnvironments) {
        return 0;
    }

}
