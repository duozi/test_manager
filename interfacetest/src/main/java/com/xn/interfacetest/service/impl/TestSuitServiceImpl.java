/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dao.RelationSuitCaseMapper;
import com.xn.interfacetest.dao.TestInterfaceMapper;
import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.*;
import com.xn.interfacetest.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TestSuitMapper;


/**
 * TestSuit Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestSuitServiceImpl implements TestSuitService {
    private static final Logger logger = LoggerFactory.getLogger(TestSuitServiceImpl.class);

    private static final String reportName = "report";

    private static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    /**
     *  Dao
     */
    @Autowired
    private TestSuitMapper testSuitMapper;

    @Autowired
    private TestSystemService testSystemService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private RelationSuitCaseService relationSuitCaseService;

    @Autowired
    private TestInterfaceMapper testInterfaceMapper;

    @Autowired
    private RelationSuitCaseMapper relationSuitCaseMapper;

    @Autowired
    private TestInterfaceService testInterfaceService;

    @Autowired
    private TestReportService testReportService;

    @Autowired
    private TestPlanService testPlanService;

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
        if(null == testSuitDto.getId()){
            testSuitMapper.save(testSuit);
        } else {
            testSuitMapper.update(testSuit);
        }

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

    @Override
    public List<TestSuitDto> listWithSystemAndInterface(Map<String, Object> params) {
        List<TestSuit> list = testSuitMapper.list(params);
        List<TestSuitDto> dtoList = CollectionUtils.transform(list, TestSuitDto.class);
        for(TestSuitDto suitDto: dtoList){
            //根据测试集查询它包含的接口信息放入结果集
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("suitId",suitDto.getId());
            List<TestInterfaceDto> interfaceDtoList = relationSuitCaseService.listGroupByInterface(paramsMap);
            suitDto.setInterfaceList(interfaceDtoList);

            //查询系统信息
            if(null != suitDto.getSystemId()){
                TestSystemDto testSystemDto = testSystemService.get(suitDto.getSystemId());
                if(null != testSystemDto){
                    suitDto.setTestSystemDto(testSystemDto);
                }
            }
        }
        return dtoList;
    }

    @Override
    public List<TestSuitDto> getSuitByCaseId(Long id) {
        List<TestSuit> testSuitList = testSuitMapper.getSuitByCaseId(id);
        List<TestSuitDto> dtoList = CollectionUtils.transform(testSuitList, TestSuitDto.class);
        return dtoList;
    }

    @Override
    public List<TestSuitDto> getByPlanId(Long id) {
        List<TestSuit> testSuitList = testSuitMapper.getByPlanId(id);
        List<TestSuitDto> dtoList = CollectionUtils.transform(testSuitList, TestSuitDto.class);
        return dtoList;
    }

    @Override
    public void excuteSuitList(List<TestSuitDto> testSuitDtoList, TestEnvironmentDto testEnvironmentDto,Long planId)  throws Exception{
        TestPlanDto testPlanDto = testPlanService.get(planId);
        //计划执行过程中将所有的相关测试集、测试用例、测试环境锁定并且不允许修改和删除
        lockPlanIn(testSuitDtoList,testEnvironmentDto,planId);


        //预保存执行结果：
        TestReportDto testReportDto = new TestReportDto();
        testReportDto.setPlanId(planId);
        Date dataTime = new Date();
        testReportDto.setName(testPlanDto.getName() + "-"+ reportName + format.format(dataTime));
        testReportDto = testReportService.save(testReportDto);

        logger.info("==========遍历测试集========");
        //遍历测试集执行测试用例
        for(TestSuitDto testSuitDto:testSuitDtoList){
            this.excuteSuit(testSuitDto,testEnvironmentDto, planId,testReportDto.getId());
        }
    }

    /**
     * 修改计划相关测试集、执行环境、测试用例、接口等信息的状态，使其不能修改
     * @param testSuitDtoList
     * @param testEnvironmentDto
     * @param planId
     */
    private void lockPlanIn(List<TestSuitDto> testSuitDtoList, TestEnvironmentDto testEnvironmentDto, Long planId) {


    }

    /**
     * 以测试集为维度在指定环境执行测试用例
     * @param testSuitDto
     * @param testEnvironmentDto
     * @param id
     */
    private void excuteSuit(TestSuitDto testSuitDto, TestEnvironmentDto testEnvironmentDto, Long planId, Long reportId)  throws Exception {
        //得到测试集的所有用例信息（因为集成测试用例并没有接口信息，所以直接取测试集下的所有用例来执行）
        List<TestCaseDto> testCaseDtoList = testCaseService.listBySuitId(testSuitDto.getId());
        //用例列表不为空则执行测试用例
        if(null != testCaseDtoList && testCaseDtoList.size() > 0){
            logger.info("==========遍历执行测试集========");
            testCaseService.excuteCaseList(testCaseDtoList,testEnvironmentDto,planId,reportId);
        }
    }
}
