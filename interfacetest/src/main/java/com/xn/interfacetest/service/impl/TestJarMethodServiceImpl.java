/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.xn.interfacetest.entity.TestJarMethod;
import com.xn.interfacetest.dto.TestJarMethodDto;
import com.xn.interfacetest.api.TestJarMethodService;
import com.xn.interfacetest.dao.TestJarMethodMapper;


/**
 * TestJarMethod Service实现
 * 
 * @author Carol
 * @date 2017-05-05
 */
@Service
@Transactional
public class TestJarMethodServiceImpl implements TestJarMethodService {

    /**
     *  Dao
     */
    @Autowired
    private TestJarMethodMapper testJarMethodMapper;

    @Override
    @Transactional(readOnly = true)
    public TestJarMethodDto get(Object condition)  
	{  
        TestJarMethod testJarMethod = testJarMethodMapper.get(condition);
        TestJarMethodDto testJarMethodDto = BeanUtils.toBean(testJarMethod,TestJarMethodDto.class);
	    return testJarMethodDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestJarMethodDto condition) {
        return testJarMethodMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestJarMethodDto> list(TestJarMethodDto condition) {
        List<TestJarMethod> list = testJarMethodMapper.list(condition);
        List<TestJarMethodDto> dtoList = CollectionUtils.transform(list, TestJarMethodDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestJarMethodDto> list(Map<String,Object> condition) {
        List<TestJarMethod> list = testJarMethodMapper.list(condition);
        List<TestJarMethodDto> dtoList = CollectionUtils.transform(list, TestJarMethodDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestJarMethodDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestJarMethodDto save(TestJarMethodDto testJarMethodDto) {
        TestJarMethod testJarMethod = BeanUtils.toBean(testJarMethodDto,TestJarMethod.class);
        if(null == testJarMethodDto.getId()){
            testJarMethodMapper.save(testJarMethod);
        } else {
            testJarMethodMapper.update(testJarMethod);
        }

        testJarMethodDto.setId(testJarMethod.getId());
        return testJarMethodDto;
    }

    @Override
    public int save(List<TestJarMethodDto> testJarMethodDtos) {
        if (testJarMethodDtos == null || testJarMethodDtos.isEmpty()) {
            return 0;
        }
        List<TestJarMethod> testJarMethods = CollectionUtils.transform(testJarMethodDtos, TestJarMethod.class);
        return testJarMethodMapper.saveBatch(testJarMethods);
    }

    @Override
    public int update(TestJarMethodDto testJarMethodDto) {
        TestJarMethod testJarMethod = BeanUtils.toBean(testJarMethodDto,TestJarMethod.class);
        return testJarMethodMapper.update(testJarMethod);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testJarMethodMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestJarMethodDto testJarMethodDto) {
        TestJarMethod testJarMethod = BeanUtils.toBean(testJarMethodDto,TestJarMethod.class);
        return testJarMethodMapper.delete(testJarMethod);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testJarMethodMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestJarMethodDto> testJarMethods) {
        return 0;
    }

    @Override
    public TestJarMethodDto getByMethodNameAndInterfaceId(String methodName,Long interfaceId) {
        TestJarMethod testJarMethod = testJarMethodMapper.getByMethodNameAndInterfaceId(methodName,interfaceId);
        TestJarMethodDto testJarMethodDto = BeanUtils.toBean(testJarMethod,TestJarMethodDto.class);
        return testJarMethodDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestJarMethodDto> getByInterfaceId(Long interfaceId) {
        List<TestJarMethod> list = testJarMethodMapper.getByInterfaceId(interfaceId);
        List<TestJarMethodDto> dtoList = CollectionUtils.transform(list, TestJarMethodDto.class);
        return dtoList;
    }

}
