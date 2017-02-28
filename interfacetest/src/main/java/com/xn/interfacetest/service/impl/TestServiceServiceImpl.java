/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestServiceMapper;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.entity.TestService;
import com.xn.interfacetest.service.TestServiceService;



/**
 * TestService Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service("testServiceService")
@Transactional
public class TestServiceServiceImpl implements TestServiceService {

    /**
     *  Dao
     */
    @Autowired
    private TestServiceMapper testServiceMapper;

    @Autowired
    private TestSystemService testSystemService;

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
        if(null != testServiceDto.getId()){
            //不为空就说明是更新操作
            testServiceMapper.update(testService);
        } else {
            //为空就是新增操作
            testServiceMapper.save(testService);
        }
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

    @Override
    public List<TestServiceDto> listByParams(Map<String, Object> params) {
        List<TestService> list = testServiceMapper.listByParams(params);
        List<TestServiceDto> dtoList = CollectionUtils.transform(list, TestServiceDto.class);
        for(TestServiceDto serviceDto: dtoList){
            TestSystemDto systemDto = testSystemService.getWithCompanyInfo(serviceDto.getSystemId());
            serviceDto.setSystemDto(systemDto);
        }
        return dtoList;
    }

}
