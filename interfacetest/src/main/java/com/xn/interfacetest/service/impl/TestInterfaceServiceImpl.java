/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.TestSuitDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.TestInterfaceService;
import com.xn.interfacetest.api.TestServiceService;
import com.xn.interfacetest.dao.TestInterfaceMapper;
import com.xn.interfacetest.dao.TestParamsMapper;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.entity.TestInterface;
import com.xn.interfacetest.entity.TestParams;
import com.xn.interfacetest.util.CollectionUtils;

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
        //保存接口字段
        if(null != testInterfaceDto.getId()){
            //保存参数字段到参数表
            saveParams(testInterface);
            testInterfaceMapper.update(testInterface);
        }else{
            testInterfaceMapper.save(testInterface);
            testInterfaceDto.setId(testInterface.getId());
            //保存参数字段到参数表
            saveParams(testInterface);
        }
        return testInterfaceDto;
    }

    private void saveParams(TestInterface testInterface){
        TestInterface testInterfaceExist = testInterfaceMapper.get(testInterface.getId());
        if(null != testInterfaceExist){
            String existParams = testInterfaceExist.getParams();
            String newParams = testInterface.getParams();
            if(StringUtils.isNotBlank(newParams) && !newParams.equals(existParams)){
                String[] existArray = existParams.split(",|，");
                //把字符串数组转为集合
                List<String> existlist = new ArrayList<String>();
                if(null != existArray && existArray.length>0){
                    existlist = Arrays.asList(existArray);
                }

                String[] newArray = newParams.split(",|，");
                List<String> newlist = new ArrayList<String>();
                if(null != newArray && newArray.length>0){
                     newlist = Arrays.asList(newArray);
                }

                //比较元素是否存在，存在的不变化，不存在的增加，
                for(String existParam:existlist){
                    //新参数列表中不包含的字段-删除
                    if(!newlist.contains(existParam)){
                        testParamsMapper.deleteByInterfaceIdAndParamName(testInterface.getId(),existParam);
                    }
                }

                for(String newParam:newlist){
                    //旧参数列表中不包含的字段-新增
                    if(!existlist.contains(newParam)){
                        TestParams paramsExist = testParamsMapper.getParamsByInterfaceIdAndName(testInterface.getId(),newParam,0);
                        //同一接口下不能存在同名的参数，不存在再保存
                        if(null == paramsExist){
                            TestParams testParams = new TestParams();
                            testParams.setInterfaceId(testInterface.getId());
                            testParams.setName(newParam);
                            //普通参数
                            if(!newParam.equals("-d")){
                                testParams.setFormatType(2);
                            } else {
                                testParams.setFormatType(1);
                            }
                            testParamsMapper.save(testParams);
                        }
                    }

                }
            }
        }
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
    public List<TestInterfaceDto> listAllBySuitId(Long suitId) {
        List<TestInterface> list = testInterfaceMapper.listAllBySuitId(suitId);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        return dtoList;
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

    @Override
    public TestInterfaceDto getByCaseId(Long caseId) {
        TestInterface testInterface = testInterfaceMapper.getByCaseId(caseId);
        TestInterfaceDto testInterfaceDto = BeanUtils.toBean(testInterface,TestInterfaceDto.class);
        return testInterfaceDto;
    }

    @Override
    public List<TestInterfaceDto> listWithInfoByIds(String[] interfaceArray) {
        List<TestInterface> list = testInterfaceMapper.listWithInfoByIds(interfaceArray);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        for(TestInterfaceDto testInterfaceDto: dtoList){
            TestServiceDto serviceDto = testServiceService.getWithSystem(testInterfaceDto.getServiceId());
            testInterfaceDto.setTestServiceDto(serviceDto);
        }
        return dtoList;
    }

    @Override
    public List<TestInterfaceDto> getByInterfaceIds(String interfaceIds) {
        String[] interfaceArray = interfaceIds.split(",|，");
        List<TestInterface> list = testInterfaceMapper.listWithInfoByIds(interfaceArray);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        return dtoList;
    }

    @Override
    public void changeStatusList(int status, List<TestInterfaceDto> interfaceIdList) {
        testInterfaceMapper.changeStatusList(status, interfaceIdList);
    }

    @Override
    public List<TestInterfaceDto> listAllBySuitList(List<TestSuitDto> testSuitDtoList) {
        List<TestInterface> list = testInterfaceMapper.listAllBySuitList(testSuitDtoList);
        List<TestInterfaceDto> dtoList = CollectionUtils.transform(list, TestInterfaceDto.class);
        return dtoList;
    }
}
