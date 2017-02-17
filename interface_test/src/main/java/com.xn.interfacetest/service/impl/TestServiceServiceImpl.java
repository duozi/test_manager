/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import com.xn.common.entity.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestServiceMapper;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.entity.TestService;
import com.xn.interfacetest.service.TestServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



/**
 * TestService Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestServiceServiceImpl implements TestServiceService {

    /**
     *  Dao
     */
    @Autowired
    private TestServiceMapper testServiceMapper;

    @Override
    @Transactional(readOnly = true)
    public TestServiceDto get(Object condition)
	{  
        TestService testService = testServiceMapper.get(condition);
        TestServiceDto testServiceDto = BeanUtils.toBean(testService,TestServiceDto.class);
	    return testServiceDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestServiceDto condition) {
        return testServiceMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestServiceDto> list(TestServiceDto condition) {
        List<TestService> list = testServiceMapper.list(condition);
        List<TestServiceDto> dtoList = CollectionUtils.transform(list, TestServiceDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestServiceDto> list(Map<String,Object> condition) {
        List<TestService> list = testServiceMapper.list(condition);
        List<TestServiceDto> dtoList = CollectionUtils.transform(list, TestServiceDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestServiceDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestServiceDto save(TestServiceDto testServiceDto) {
        TestService testService = BeanUtils.toBean(testServiceDto,TestService.class);
        testServiceMapper.save(testService);
        testServiceDto.setId(testService.getId());
        return testServiceDto;
    }

    @Override
    public int save(List<TestServiceDto> testServiceDtos) {
        if (testServiceDtos == null || testServiceDtos.isEmpty()) {
            return 0;
        }
        List<TestService> testServices = CollectionUtils.transform(testServiceDtos, TestService.class);
        return testServiceMapper.saveBatch(testServices);
    }

    @Override
    public int update(TestServiceDto testServiceDto) {
        TestService testService = BeanUtils.toBean(testServiceDto,TestService.class);
        return testServiceMapper.update(testService);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testServiceMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestServiceDto testServiceDto) {
        TestService testService = BeanUtils.toBean(testServiceDto,TestService.class);
        return testServiceMapper.delete(testService);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testServiceMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestServiceDto> testServices) {
        return 0;
    }

}
