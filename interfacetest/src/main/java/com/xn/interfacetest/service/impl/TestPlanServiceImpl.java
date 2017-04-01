/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.common.base.CommonResult;
import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.RelationPlanEnvironment;
import com.xn.interfacetest.entity.RelationPlanSuit;
import com.xn.interfacetest.entity.TestEnvironment;
import com.xn.interfacetest.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestPlanMapper;
import com.xn.interfacetest.entity.TestPlan;


/**
 * TestPlan Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestPlanServiceImpl implements TestPlanService {
    private static final Logger logger = LoggerFactory.getLogger(TestPlanServiceImpl.class);
    /**
     *  Dao
     */
    @Autowired
    private TestPlanMapper testPlanMapper;

    @Autowired
    private TestEnvironmentService testEnvironmentService;

    @Autowired
    private TestSuitService testSuitService;

    @Autowired
    private RelationPlanEnvironmentService relationPlanEnvironmentService;

    @Autowired
    private RelationPlanSuitService relationPlanSuitService;

    @Autowired
    private TimeConfigService timeConfigService;

    @Override
    @Transactional(readOnly = true)
    public TestPlanDto get(Object condition)
	{  
        TestPlan testPlan = testPlanMapper.get(condition);
        TestPlanDto testPlanDto = BeanUtils.toBean(testPlan,TestPlanDto.class);
	    return testPlanDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestPlanDto condition) {
        return testPlanMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestPlanDto> list(TestPlanDto condition) {
        List<TestPlan> list = testPlanMapper.list(condition);
        List<TestPlanDto> dtoList = CollectionUtils.transform(list, TestPlanDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestPlanDto> list(Map<String,Object> condition) {
        List<TestPlan> list = testPlanMapper.list(condition);
        List<TestPlanDto> dtoList = CollectionUtils.transform(list, TestPlanDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestPlanDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestPlanDto save(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        testPlanMapper.save(testPlan);
        testPlanDto.setId(testPlan.getId());
        return testPlanDto;
    }

    @Override
    public int save(List<TestPlanDto> testPlanDtos) {
        if (testPlanDtos == null || testPlanDtos.isEmpty()) {
            return 0;
        }
        List<TestPlan> testPlans = CollectionUtils.transform(testPlanDtos, TestPlan.class);
        return testPlanMapper.saveBatch(testPlans);
    }

    @Override
    public int update(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        return testPlanMapper.update(testPlan);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testPlanMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestPlanDto testPlanDto) {
        TestPlan testPlan = BeanUtils.toBean(testPlanDto,TestPlan.class);
        return testPlanMapper.delete(testPlan);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testPlanMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestPlanDto> testPlans) {
        return 0;
    }

    @Override
    public List<TestPlanDto> listWithOtherInformation(Map<String, Object> params) {
        List<TestPlan> list = testPlanMapper.list(params);
        List<TestPlanDto> dtoList = CollectionUtils.transform(list, TestPlanDto.class);
        for(TestPlanDto testPlanDto:dtoList){
            //查询环境信息
            List<TestEnvironmentDto> environmentDtoList = testEnvironmentService.getByPlanId(testPlanDto.getId());
            testPlanDto.setEnvironmentList(environmentDtoList);
            //查询测试集列表
            List<TestSuitDto> testSuitDtoList = testSuitService.getByPlanId(testPlanDto.getId());
            testPlanDto.setSuitList(testSuitDtoList);
        }
        return dtoList;
    }

    @Override
    public void publishPlan(Integer status,Long id) {
        testPlanMapper.updateStatus(status,id);
    }

    @Override
    public CommonResult excutePlan(Long planId) {
        CommonResult result = new CommonResult();
        //读取测试集
        List<TestSuitDto> testSuitDtoList = testSuitService.getByPlanId(planId);
        if(null == testSuitDtoList || testSuitDtoList.size() <= 0){
            logger.info("校验测试集列表｛｝：没有可执行测试集");
            result.setMessage("没有可执行测试集！");
            result.setCode(0);
            return result;
        }

        //读取执行环境
        List<TestEnvironmentDto> testEnvironmentDtoList = testEnvironmentService.getByPlanId(planId);
        if(null == testEnvironmentDtoList || testEnvironmentDtoList.size() <= 0){
            logger.info("校验测试环境列表｛｝：没有可执行测试环境");
            result.setMessage("没有可执行测试环境！");
            result.setCode(0);
            return result;
        }

        //获取执行时间---即刻执行
        List<TimeConfigDto> timeConfigDtoList = timeConfigService.getByPlanId(planId);
//        if(null == timeConfigDtoList || timeConfigDtoList.size() <= 0){
//            result.setMessage("没有设置执行时间！");
//            result.setCode(0);
//            return result;
//        }

        return this.excutePlan(testSuitDtoList,testEnvironmentDtoList,timeConfigDtoList, planId);
    }

    private CommonResult excutePlan(List<TestSuitDto> testSuitDtoList,List<TestEnvironmentDto> testEnvironmentDtoList,List<TimeConfigDto> timeConfigDtoList,Long planId){
        CommonResult result = new CommonResult();

        //遍历执行环境，在每一套环境上执行一次
        logger.info("==========遍历测试环境========");
        for(TestEnvironmentDto testEnvironmentDto : testEnvironmentDtoList){
            //执行测试集
            testSuitService.excuteSuitList(testSuitDtoList,testEnvironmentDto,planId);
        }
        return result;
    }

}
