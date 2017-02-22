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
import com.xn.interfacetest.dao.TestSuitMapper;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.entity.TestSuit;
import com.xn.interfacetest.service.TestSuitService;


/**
 * TestSuit Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestSuitServiceImpl implements TestSuitService {

    /**
     *  Dao
     */
    @Autowired
    private TestSuitMapper testSuitMapper;

    @Override
    @Transactional(readOnly = true)
    public TestSuitDto get(Object condition)
	{  
        TestSuit testSuit = testSuitMapper.get(condition);
        TestSuitDto testSuitDto = BeanUtils.toBean(testSuit,TestSuitDto.class);
	    return testSuitDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestSuitDto condition) {
        return testSuitMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSuitDto> list(TestSuitDto condition) {
        List<TestSuit> list = testSuitMapper.list(condition);
        List<TestSuitDto> dtoList = CollectionUtils.transform(list, TestSuitDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSuitDto> list(Map<String,Object> condition) {
        List<TestSuit> list = testSuitMapper.list(condition);
        List<TestSuitDto> dtoList = CollectionUtils.transform(list, TestSuitDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestSuitDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestSuitDto save(TestSuitDto testSuitDto) {
        TestSuit testSuit = BeanUtils.toBean(testSuitDto,TestSuit.class);
        testSuitMapper.save(testSuit);
        testSuitDto.setId(testSuit.getId());
        return testSuitDto;
    }

    @Override
    public int save(List<TestSuitDto> testSuitDtos) {
        if (testSuitDtos == null || testSuitDtos.isEmpty()) {
            return 0;
        }
        List<TestSuit> testSuits = CollectionUtils.transform(testSuitDtos, TestSuit.class);
        return testSuitMapper.saveBatch(testSuits);
    }

    @Override
    public int update(TestSuitDto testSuitDto) {
        TestSuit testSuit = BeanUtils.toBean(testSuitDto,TestSuit.class);
        return testSuitMapper.update(testSuit);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testSuitMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestSuitDto testSuitDto) {
        TestSuit testSuit = BeanUtils.toBean(testSuitDto,TestSuit.class);
        return testSuitMapper.delete(testSuit);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testSuitMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestSuitDto> testSuits) {
        return 0;
    }

}
