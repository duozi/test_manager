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
import com.xn.interfacetest.dao.TestRedisConfigMapper;
import com.xn.interfacetest.dto.TestRedisConfigDto;
import com.xn.interfacetest.entity.TestRedisConfig;
import com.xn.interfacetest.service.TestRedisConfigService;


/**
 * TestRedisConfig Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestRedisConfigServiceImpl implements TestRedisConfigService {

    /**
     *  Dao
     */
    @Autowired
    private TestRedisConfigMapper testRedisConfigMapper;

    @Override
    @Transactional(readOnly = true)
    public TestRedisConfigDto get(Object condition)
	{  
        TestRedisConfig testRedisConfig = testRedisConfigMapper.get(condition);
        TestRedisConfigDto testRedisConfigDto = BeanUtils.toBean(testRedisConfig,TestRedisConfigDto.class);
	    return testRedisConfigDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestRedisConfigDto condition) {
        return testRedisConfigMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestRedisConfigDto> list(TestRedisConfigDto condition) {
        List<TestRedisConfig> list = testRedisConfigMapper.list(condition);
        List<TestRedisConfigDto> dtoList = CollectionUtils.transform(list, TestRedisConfigDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestRedisConfigDto> list(Map<String,Object> condition) {
        List<TestRedisConfig> list = testRedisConfigMapper.list(condition);
        List<TestRedisConfigDto> dtoList = CollectionUtils.transform(list, TestRedisConfigDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestRedisConfigDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestRedisConfigDto save(TestRedisConfigDto testRedisConfigDto) {
        TestRedisConfig testRedisConfig = BeanUtils.toBean(testRedisConfigDto,TestRedisConfig.class);
        testRedisConfigMapper.save(testRedisConfig);
        testRedisConfigDto.setId(testRedisConfig.getId());
        return testRedisConfigDto;
    }

    @Override
    public int save(List<TestRedisConfigDto> testRedisConfigDtos) {
        if (testRedisConfigDtos == null || testRedisConfigDtos.isEmpty()) {
            return 0;
        }
        List<TestRedisConfig> testRedisConfigs = CollectionUtils.transform(testRedisConfigDtos, TestRedisConfig.class);
        return testRedisConfigMapper.saveBatch(testRedisConfigs);
    }

    @Override
    public int update(TestRedisConfigDto testRedisConfigDto) {
        TestRedisConfig testRedisConfig = BeanUtils.toBean(testRedisConfigDto,TestRedisConfig.class);
        return testRedisConfigMapper.update(testRedisConfig);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testRedisConfigMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestRedisConfigDto testRedisConfigDto) {
        TestRedisConfig testRedisConfig = BeanUtils.toBean(testRedisConfigDto,TestRedisConfig.class);
        return testRedisConfigMapper.delete(testRedisConfig);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testRedisConfigMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestRedisConfigDto> testRedisConfigs) {
        return 0;
    }

}
