/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xn.interfacetest.Enum.PlanStatusEnum;
import com.xn.interfacetest.api.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.base.CommonResult;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.RelationSuitCaseMapper;
import com.xn.interfacetest.dao.TestInterfaceMapper;
import com.xn.interfacetest.dao.TestSuitMapper;
import com.xn.interfacetest.dto.TestCaseDto;
import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestReportDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.entity.TestSuit;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.util.CollectionUtils;


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
    public static ExecutorService exe = Executors.newFixedThreadPool(50);
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

    @Autowired
    private TestEnvironmentService testEnvironmentService;

    @Autowired
    private TestSuitService testSuitService;

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
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public CommonResult excuteSuitList(List<TestSuitDto> testSuitDtoList, TestEnvironmentDto testEnvironmentDto, Long planId) throws Exception{
        CommonResult result = new CommonResult();
        TestReportDto testReportDto = new TestReportDto();
        try{
            ExecutorService exe = Executors.newFixedThreadPool(50);

            TestPlanDto testPlanDto = testPlanService.get(planId);
            //计划执行过程中将所有的相关测试集、测试用例、测试环境锁定并且不允许修改和删除
            changePlanInfo(testSuitDtoList,testEnvironmentDto,planId ,PlanStatusEnum.EXCUTING.getId());

            ReportResult.resetReportResult();
            ReportResult.getReportResult().setStartTime(new Date());
            logger.info("开始执行测试集的时候reportResult的值：" +  ReportResult.getReportResult().toString());

            testReportDto = initTestReport(testEnvironmentDto,testPlanDto,testReportDto,testSuitDtoList);
            result.setData(testReportDto);

            excute(testSuitDtoList, testEnvironmentDto, planId,testReportDto);
        }catch (Exception e){
            logger.error("异常：｛｝",e);
            result.setCode(1);
            result.setMessage("执行计划异常：" + e.getMessage());
            throw e;
        } finally {
            //解除锁定
            changePlanInfo(testSuitDtoList, testEnvironmentDto, planId,PlanStatusEnum.EXECUTED.getId());
        }
        //保存执行时间和执行结果
        updateReportResult(testReportDto);

        return result;
    }

    private TestReportDto initTestReport(TestEnvironmentDto testEnvironmentDto, TestPlanDto testPlanDto, TestReportDto testReportDto, List<TestSuitDto> testSuitDtoList) {
        //设置计划id
        testReportDto.setPlanId(testPlanDto.getId());
        testReportDto.setEnvironmentId(testEnvironmentDto.getId());
        //设置报告名称
        Date dataTime = new Date();
        testReportDto.setName(testPlanDto.getName() + "-"+ reportName + format.format(dataTime));

        if(null != testSuitDtoList && testSuitDtoList.size() > 0){
            String suitIds = "";
            for(TestSuitDto testsuit : testSuitDtoList){
                suitIds = testsuit.getId() + ",";
            }
            testReportDto.setSuitIds(StringUtils.isNotBlank(suitIds)?suitIds.substring(0,suitIds.length()-1):null);

            //查出所有关联的接口
            List<TestInterfaceDto> testInterfaceDtoList = testInterfaceService.listAllBySuitList(testSuitDtoList);
            if(null != testInterfaceDtoList && testInterfaceDtoList.size() > 0){
                String interfaceIds = "";
                for(TestInterfaceDto testInterfaceDto : testInterfaceDtoList){
                    interfaceIds += testInterfaceDto.getId() + ",";
                }
                testReportDto.setInterfaceIds(StringUtils.isNotBlank(interfaceIds)?interfaceIds.substring(0,interfaceIds.length()-1):null);
            }

            //查出所有的用例
            List<TestCaseDto> testCaseDtoList = testCaseService.listAllBySuitList(testSuitDtoList);
            if(null != testCaseDtoList && testCaseDtoList.size() > 0){
                String caseIds = "";
                for(TestCaseDto testCaseDto : testCaseDtoList){
                    caseIds += testCaseDto.getId() + ",";
                    logger.info("caseid:" + testCaseDto.getId());
                }
                logger.info("caseids:" + caseIds);
                testReportDto.setCaseIds(StringUtils.isNotBlank(caseIds)?caseIds.substring(0,caseIds.length()-1):null);
            }
        }
        testReportDto = testReportService.save(testReportDto);
        logger.info("初始化report:" + testReportDto.toString());
        return testReportDto;
    }

    @Override
    public List<TestSuitDto> listAll() {
        List<TestSuit> list = testSuitMapper.list(new TestSuit());
        List<TestSuitDto> dtoList = CollectionUtils.transform(list, TestSuitDto.class);
        return dtoList;
    }

    @Override
    public void changeStatus(int status, List<TestSuitDto> testSuitDtoList) {
        if(null != testSuitDtoList && testSuitDtoList.size()>0){
            for(TestSuitDto testSuit: testSuitDtoList){
                //修改测试集的状态
                testSuitMapper.changeStatus(status, testSuit.getId());
                //修改用例的状态
                List<TestCaseDto> testCaseDtoList = testCaseService.listBySuitId(testSuit.getId());
                testCaseService.changeStatusList(status,testCaseDtoList);

                //修改接口的状态
                List<TestInterfaceDto> interfaceIdList = testInterfaceService.listAllBySuitId(testSuit.getId());
                testInterfaceService.changeStatusList(status,interfaceIdList);
             }
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private void excute(List<TestSuitDto> testSuitDtoList, TestEnvironmentDto testEnvironmentDto, Long planId,TestReportDto testReportDto) throws Exception{
        String systemIds = "";
        for (int i = 0; i < testSuitDtoList.size(); i++) {
            try {
                //系统id
                systemIds = testSuitDtoList.get(i).getSystemId() + ",";
                //执行单个测试集
                excuteSuit(testSuitDtoList.get(i),testEnvironmentDto, planId,testReportDto);
            } catch (Exception e) {
                logger.error("测试集｛｝执行异常:"+testSuitDtoList.get(i).getName(),e);
                throw e;
            }
        }
        testReportDto.setSystemIds(StringUtils.isNotBlank(systemIds)?systemIds.substring(0,systemIds.length()-1):null);
    }


    private void updateReportResult(TestReportDto testReportDto) {
        ReportResult.getReportResult().setStopTime(new Date());

        //保存计划执行时间
        testReportDto.setBeginTime(format.format(ReportResult.getReportResult().getStartTime()));
        testReportDto.setEndTime(format.format(ReportResult.getReportResult().getStopTime()));
        testReportDto.setCostTime(Integer.valueOf(ReportResult.getReportResult().getTimeLong()));
        testReportDto.setCaseCount((long) ReportResult.getReportResult().getTotal());
        testReportDto.setErrorCaseCout((long) ReportResult.getReportResult().getError());
        testReportDto.setFailCaseCout((long) ReportResult.getReportResult().getFailed());
        testReportDto.setPassCaseCount((long) ReportResult.getReportResult().getTotal()-(long) ReportResult.getReportResult().getFailed()-(long) ReportResult.getReportResult().getError());
        logger.info("更新计划执行时间:{}"+testReportDto.toString());
        testReportService.update(testReportDto);

        //重置结果计数类
        ReportResult.resetReportResult();
    }

    /**
     * 修改计划相关测试集、执行环境、测试用例、接口等信息的状态，使其不能修改
     * @param testSuitDtoList
     * @param testEnvironmentDto
     * @param planId
     */
    private void changePlanInfo(List<TestSuitDto> testSuitDtoList, TestEnvironmentDto testEnvironmentDto, Long planId,int status) {
        //将测试计划更新为执行中---执行中计划不可修改
        testPlanService.changeStatusOfPlan(status,planId);
        //将测试集更新为执行中---执行中计划不可修改
        this.changeStatus(status,testSuitDtoList);
        //将测试集更新为执行中---执行中计划不可修改
        testEnvironmentService.changeStatus(status,testEnvironmentDto.getId());
    }

    /**
     * 以测试集为维度在指定环境执行测试用例
     * @param testSuitDto
     * @param testEnvironmentDto
     * @param testReportDto
     */
    private void excuteSuit(TestSuitDto testSuitDto, TestEnvironmentDto testEnvironmentDto, Long planId, TestReportDto testReportDto) {
        logger.info("==========执行测试集========");
        //得到测试集的所有用例信息（因为集成测试用例并没有接口信息，所以直接取测试集下的所有用例来执行）
        List<TestCaseDto> testCaseDtoList = testCaseService.listBySuitId(testSuitDto.getId());
        //用例列表不为空则执行测试用例
        if(null != testCaseDtoList && testCaseDtoList.size() > 0){
            logger.info("==========遍历执行测试集========");
            try{

                testCaseService.excuteCaseList(testCaseDtoList,testEnvironmentDto,planId,testReportDto,testSuitDto);
            }catch (Exception e){
                logger.error("执行测试集异常｛｝",e);
            }

        }
    }
}
