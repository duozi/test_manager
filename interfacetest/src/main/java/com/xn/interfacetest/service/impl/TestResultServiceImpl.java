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
import com.xn.interfacetest.dao.TestResultMapper;
import com.xn.interfacetest.dto.TestResultDto;
import com.xn.interfacetest.entity.TestResult;
import com.xn.interfacetest.service.TestResultService;



/**
 * TestResult Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestResultServiceImpl implements TestResultService {

    /**
     *  Dao
     */
    @Autowired
    private TestResultMapper testResultMapper;

    @Override
    @Transactional(readOnly = true)
    public TestResultDto get(Object condition)
	{  
        TestResult testResult = testResultMapper.get(condition);
        TestResultDto testResultDto = BeanUtils.toBean(testResult,TestResultDto.class);
	    return testResultDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestResultDto condition) {
        return testResultMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestResultDto> list(TestResultDto condition) {
        List<TestResult> list = testResultMapper.list(condition);
        List<TestResultDto> dtoList = CollectionUtils.transform(list, TestResultDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestResultDto> list(Map<String,Object> condition) {
        List<TestResult> list = testResultMapper.list(condition);
        List<TestResultDto> dtoList = CollectionUtils.transform(list, TestResultDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestResultDto save(TestResultDto testResultDto) {
        TestResult testResult = BeanUtils.toBean(testResultDto,TestResult.class);
        testResultMapper.save(testResult);
        testResultDto.setId(testResult.getId());
        return testResultDto;
    }

    @Override
    public int save(List<TestResultDto> testResultDtos) {
        if (testResultDtos == null || testResultDtos.isEmpty()) {
            return 0;
        }
        List<TestResult> testResults = CollectionUtils.transform(testResultDtos, TestResult.class);
        return testResultMapper.saveBatch(testResults);
    }

    @Override
    public int update(TestResultDto testResultDto) {
        TestResult testResult = BeanUtils.toBean(testResultDto,TestResult.class);
        return testResultMapper.update(testResult);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestResultDto testResultDto) {
        TestResult testResult = BeanUtils.toBean(testResultDto,TestResult.class);
        return testResultMapper.delete(testResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestResultDto> testResults) {
        return 0;
    }

}
