/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dao.TestParamsMapper;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.entity.TestParams;
import com.xn.interfacetest.entity.TestService;
import com.xn.interfacetest.service.TestParamsService;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestInterfaceMapper;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.entity.TestInterface;
import com.xn.interfacetest.service.TestInterfaceService;

/**
 * TestInterface Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestInterfaceServiceImpl implements TestInterfaceService {

    /**
     *  Dao
     */
    @Autowired
    private TestInterfaceMapper testInterfaceMapper;

    @Autowired
    private TestServiceService testServiceService;

    @Autowired
    private TestParamsMapper testParamsMapper;

    @Override
    @Transactional(readOnly = true)
    public TestInterfaceDto get(Object condition)
	{  
        TestInterface testInterface = testInterfaceMapper.get(condition);
        TestInterfaceDto testInterfaceDto = BeanUtils.toBean(testInterface,TestInterfaceDto.class);
	    return testInterfaceDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestInterfaceDto condition) {
        return testInterfaceMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestInterfaceDto> list(TestInterfaceDto condition) {
        List<TestInterface> list = testInterfaceMapper.list(condition);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestInterfaceDto> list(Map<String,Object> condition) {
        List<TestInterface> list = testInterfaceMapper.list(condition);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestInterfaceDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestInterfaceDto save(TestInterfaceDto testInterfaceDto) {
        TestInterface testInterface = BeanUtils.toBean(testInterfaceDto,TestInterface.class);
        if(null != testInterfaceDto.getId()){
            testInterfaceMapper.update(testInterface);
        }else{
            testInterfaceMapper.save(testInterface);
        }

        //保存参数字段到参数表
        if(StringUtils.isNotBlank(testInterface.getParams())){
            String[] params = testInterface.getParams().split(",|，");
            for(String paramName : params){
                TestParams testParams = new TestParams();
                testParams.setInterfaceId(testInterfaceDto.getId());
                testParams.setName(paramName);
                testParamsMapper.save(testParams);
            }


        }
        testInterfaceDto.setId(testInterface.getId());
        return testInterfaceDto;
    }

    @Override
    public int save(List<TestInterfaceDto> testInterfaceDtos) {
        if (testInterfaceDtos == null || testInterfaceDtos.isEmpty()) {
            return 0;
        }
        List<TestInterface> testInterfaces = CollectionUtils.transform(testInterfaceDtos, TestInterface.class);
        return testInterfaceMapper.saveBatch(testInterfaces);
    }

    @Override
    public int update(TestInterfaceDto testInterfaceDto) {
        TestInterface testInterface = BeanUtils.toBean(testInterfaceDto,TestInterface.class);
        return testInterfaceMapper.update(testInterface);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testInterfaceMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestInterfaceDto testInterfaceDto) {
        TestInterface testInterface = BeanUtils.toBean(testInterfaceDto,TestInterface.class);
        return testInterfaceMapper.delete(testInterface);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testInterfaceMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestInterfaceDto> testInterfaces) {
        return 0;
    }

    @Override
    public List<TestInterfaceDto> listByParams(Map<String, Object> params) {
        List<TestInterface> list = testInterfaceMapper.list(params);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        for(TestInterfaceDto testInterfaceDto: dtoList){
            TestServiceDto serviceDto = testServiceService.get(testInterfaceDto.getServiceId());
            testInterfaceDto.setTestServiceDto(serviceDto);
        }
        return dtoList;
    }

    @Override
    public List<TestInterfaceDto> listAllBySuitId(Object o) {

        return null;
    }

    @Override
    public List<TestInterfaceDto> listAll() {
        List<TestInterface> list = testInterfaceMapper.list(null);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        for(TestInterfaceDto testInterfaceDto: dtoList){
            TestServiceDto serviceDto = testServiceService.getWithSystem(testInterfaceDto.getServiceId());
            testInterfaceDto.setTestServiceDto(serviceDto);
        }
        return dtoList;
    }

    @Override
    public String getParamsByInterfaceId(String interfaceId) {
        return testInterfaceMapper.getParamsByInterfaceId(interfaceId);
    }
}
