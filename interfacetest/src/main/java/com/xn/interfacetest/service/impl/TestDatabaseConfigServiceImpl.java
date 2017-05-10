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
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.TestDatabaseConfigService;
import com.xn.interfacetest.dao.TestDatabaseConfigMapper;
import com.xn.interfacetest.dto.TestDatabaseConfigDto;
import com.xn.interfacetest.entity.TestDatabaseConfig;
import com.xn.interfacetest.util.CollectionUtils;


/**
 * TestDatabaseConfig Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestDatabaseConfigServiceImpl implements TestDatabaseConfigService {

    /**
     *  Dao
     */
    @Autowired
    private TestDatabaseConfigMapper testDatabaseConfigMapper;

    @Override
    @Transactional(readOnly = true)
    public TestDatabaseConfigDto get(Object condition)
	{  
        TestDatabaseConfig testDatabaseConfig = testDatabaseConfigMapper.get(condition);
        TestDatabaseConfigDto testDatabaseConfigDto = BeanUtils.toBean(testDatabaseConfig,TestDatabaseConfigDto.class);
	    return testDatabaseConfigDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestDatabaseConfigDto condition) {
        return testDatabaseConfigMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestDatabaseConfigDto> list(TestDatabaseConfigDto condition) {
        List<TestDatabaseConfig> list = testDatabaseConfigMapper.list(condition);
        List<TestDatabaseConfigDto> dtoList = CollectionUtils.transform(list, TestDatabaseConfigDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestDatabaseConfigDto> list(Map<String,Object> condition) {
        List<TestDatabaseConfig> list = testDatabaseConfigMapper.list(condition);
        List<TestDatabaseConfigDto> dtoList = CollectionUtils.transform(list, TestDatabaseConfigDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestDatabaseConfigDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestDatabaseConfigDto save(TestDatabaseConfigDto testDatabaseConfigDto) {
        TestDatabaseConfig testDatabaseConfig = BeanUtils.toBean(testDatabaseConfigDto,TestDatabaseConfig.class);
        if(null != testDatabaseConfigDto.getId()){
            testDatabaseConfigMapper.update(testDatabaseConfig);
        } else{
            testDatabaseConfigMapper.save(testDatabaseConfig);
        }

        testDatabaseConfigDto.setId(testDatabaseConfig.getId());
        return testDatabaseConfigDto;
    }

    @Override
    public int save(List<TestDatabaseConfigDto> testDatabaseConfigDtos) {
        if (testDatabaseConfigDtos == null || testDatabaseConfigDtos.isEmpty()) {
            return 0;
        }
        List<TestDatabaseConfig> testDatabaseConfigs = CollectionUtils.transform(testDatabaseConfigDtos, TestDatabaseConfig.class);
        return testDatabaseConfigMapper.saveBatch(testDatabaseConfigs);
    }

    @Override
    public int update(TestDatabaseConfigDto testDatabaseConfigDto) {
        TestDatabaseConfig testDatabaseConfig = BeanUtils.toBean(testDatabaseConfigDto,TestDatabaseConfig.class);
        return testDatabaseConfigMapper.update(testDatabaseConfig);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testDatabaseConfigMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestDatabaseConfigDto testDatabaseConfigDto) {
        TestDatabaseConfig testDatabaseConfig = BeanUtils.toBean(testDatabaseConfigDto,TestDatabaseConfig.class);
        return testDatabaseConfigMapper.delete(testDatabaseConfig);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testDatabaseConfigMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestDatabaseConfigDto> testDatabaseConfigs) {
        return 0;
    }

    @Override
    public TestDatabaseConfigDto getByEnvironmentAndDbName(String databaseName, Long environmentId) {
        TestDatabaseConfig testDatabaseConfig = testDatabaseConfigMapper.getByEnvironmentAndDbName(databaseName,environmentId);
        TestDatabaseConfigDto testDatabaseConfigDto = BeanUtils.toBean(testDatabaseConfig,TestDatabaseConfigDto.class);
        return testDatabaseConfigDto;
    }

    @Override
    public TestDatabaseConfigDto getByName(String name) {
        TestDatabaseConfig testDatabaseConfig = testDatabaseConfigMapper.getByName(name);
        TestDatabaseConfigDto testDatabaseConfigDto = BeanUtils.toBean(testDatabaseConfig,TestDatabaseConfigDto.class);
        return testDatabaseConfigDto;
    }

}
