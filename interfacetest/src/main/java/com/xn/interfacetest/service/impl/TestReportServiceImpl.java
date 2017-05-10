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
import com.xn.interfacetest.api.TestEnvironmentService;
import com.xn.interfacetest.api.TestPlanService;
import com.xn.interfacetest.api.TestReportService;
import com.xn.interfacetest.api.TestSuitService;
import com.xn.interfacetest.dao.TestReportMapper;
import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestReportDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.entity.TestReport;
import com.xn.interfacetest.util.CollectionUtils;



/**
 * TestReport Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestReportServiceImpl implements TestReportService {

    /**
     *  Dao
     */
    @Autowired
    private TestReportMapper testReportMapper;

    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    private TestSuitService testSuitService;

    @Autowired
    private TestEnvironmentService testEnvironmentService;

    @Override
    @Transactional(readOnly = true)
    public TestReportDto get(Object condition)
	{  
        TestReport testReport = testReportMapper.get(condition);
        TestReportDto testReportDto = BeanUtils.toBean(testReport,TestReportDto.class);
	    return testReportDto;
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestReportDto condition) {
        return testReportMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestReportDto> list(TestReportDto condition) {
        List<TestReport> list = testReportMapper.list(condition);
        List<TestReportDto> dtoList = CollectionUtils.transform(list, TestReportDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestReportDto> list(Map<String,Object> condition) {
        List<TestReport> list = testReportMapper.list(condition);
        List<TestReportDto> dtoList = CollectionUtils.transform(list, TestReportDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestReportDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestReportDto save(TestReportDto testReportDto) {
        TestReport testReport = BeanUtils.toBean(testReportDto,TestReport.class);
        if(null == testReportDto.getId()){
            testReportMapper.save(testReport);
        }else {
            testReportMapper.update(testReport);
        }
        testReportDto.setId(testReport.getId());
        return testReportDto;
    }

    @Override
    public int save(List<TestReportDto> testReportDtos) {
        if (testReportDtos == null || testReportDtos.isEmpty()) {
            return 0;
        }
        List<TestReport> testReports = CollectionUtils.transform(testReportDtos, TestReport.class);
        return testReportMapper.saveBatch(testReports);
    }

    @Override
    public int update(TestReportDto testReportDto) {
        TestReport testReport = BeanUtils.toBean(testReportDto,TestReport.class);
        return testReportMapper.update(testReport);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testReportMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestReportDto testReportDto) {
        TestReport testReport = BeanUtils.toBean(testReportDto,TestReport.class);
        return testReportMapper.delete(testReport);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testReportMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestReportDto> testResults) {
        return 0;
    }

    @Override
    public List<TestReportDto> selectWithOtherInfo(Map<String, Object> params) {
        List<TestReport> list = testReportMapper.selectWithOtherInfo(params);
        List<TestReportDto> dtoList = CollectionUtils.transform(list, TestReportDto.class);
        for(TestReportDto testReportDto:dtoList){
            //查询测试计划详情
            TestPlanDto testPlanDto = testPlanService.get(testReportDto.getPlanId());
            if(null != testPlanDto){
                testReportDto.setPlan(testPlanDto);
            }
            //查询测试环境详情
            TestEnvironmentDto testEnvironmentDto = testEnvironmentService.get(testReportDto.getEnvironmentId());
            if(null != testEnvironmentDto){
                testReportDto.setEnvironment(testEnvironmentDto);
            }
            //查询出测试集
            List<TestSuitDto> testSuitDtoList = testSuitService.getByPlanId(testReportDto.getPlanId());
            if(null != testSuitDtoList && testSuitDtoList.size() > 0){
                testReportDto.setSuitList(testSuitDtoList);
            }
        }
        return dtoList;
    }

    @Override
    public TestReportDto getWithInfo(long id) {
        TestReport testReport = testReportMapper.get(id);
        TestReportDto testReportDto = BeanUtils.toBean(testReport,TestReportDto.class);
        if(null != testReportDto){
            //查询测试计划详情
            TestPlanDto testPlanDto = testPlanService.get(testReportDto.getPlanId());
            if(null != testPlanDto){
                testReportDto.setPlan(testPlanDto);
            }
            //查询测试环境详情
            TestEnvironmentDto testEnvironmentDto = testEnvironmentService.get(testReportDto.getEnvironmentId());
            if(null != testEnvironmentDto){
                testReportDto.setEnvironment(testEnvironmentDto);
            }
            //查询出测试集
            List<TestSuitDto> testSuitDtoList = testSuitService.getByPlanId(testReportDto.getPlanId());
            if(null != testSuitDtoList && testSuitDtoList.size() > 0){
                testReportDto.setSuitList(testSuitDtoList);
            }
        }
        return testReportDto;
    }

}
