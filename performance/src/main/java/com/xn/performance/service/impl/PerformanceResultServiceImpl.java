/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.dao.PerformanceResultMapper;
import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;
import com.xn.performance.entity.PerformancePlanShow;
import com.xn.performance.entity.PerformanceResult;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.service.PerformanceResultService;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * PerformanceResult Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceResultServiceImpl implements PerformanceResultService {

    /**
     *  Dao
     */
    @Autowired
    private PerformanceResultMapper performanceResultMapper;

    @Autowired
    private PerformanceScenarioServiceImpl performanceScenarioService;

    @Override
    public PerformanceResultDto get(Object condition)  
	{  
        PerformanceResult performanceResult = performanceResultMapper.get(condition);
        PerformanceResultDto performanceResultDto = BeanUtils.toBean(performanceResult,PerformanceResultDto.class);
	    return performanceResultDto;  
	}  

    @Override
    public long count(PerformanceResultDto condition) {
        return performanceResultMapper.count(condition);
    }

    @Override
    public List<PerformanceResultDto> list(PerformanceResultDto condition) {
        List<PerformanceResult> list = performanceResultMapper.list(condition);
        List<PerformanceResultDto> dtoList = CollectionUtils.transform(list, PerformanceResultDto.class);
        return dtoList;
    }

    @Override
    public List<PerformanceResultDto> list(Map<String,Object> condition) {
        List<PerformanceResult> list = performanceResultMapper.list(condition);
        List<PerformanceResultDto> dtoList = CollectionUtils.transform(list, PerformanceResultDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformanceResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceResultDto save(PerformanceResultDto performanceResultDto) {
        PerformanceResult performanceResult = BeanUtils.toBean(performanceResultDto,PerformanceResult.class);
        performanceResultMapper.save(performanceResult);
        performanceResultDto.setId(performanceResult.getId());
        return performanceResultDto;
    }

    @Override
    public int save(List<PerformanceResultDto> performanceResultDtos) {
        if (performanceResultDtos == null || performanceResultDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceResult> performanceResults = CollectionUtils.transform(performanceResultDtos, PerformanceResult.class);
        return performanceResultMapper.saveBatch(performanceResults);
    }

    @Override
    public int update(PerformanceResultDto performanceResultDto) {
        PerformanceResult performanceResult = BeanUtils.toBean(performanceResultDto,PerformanceResult.class);
        return performanceResultMapper.update(performanceResult);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performanceResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceResultDto performanceResultDto) {
        PerformanceResult performanceResult = BeanUtils.toBean(performanceResultDto,PerformanceResult.class);
        return performanceResultMapper.delete(performanceResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformanceResultDto> performanceResults) {
        return 0;
    }

    @Override
    public List<PerformancePlanShowDto> getNowTask(PerformanceResultDto performanceResultDto) {
        List<PerformancePlanShow> list=performanceResultMapper.getTask(performanceResultDto);
        List<PerformancePlanShowDto> dtoList=CollectionUtils.transform(list, PerformancePlanShowDto.class);
        List<PerformancePlanShowDto> resultList=new ArrayList<PerformancePlanShowDto>();
        for(PerformancePlanShowDto performancePlanShowDto:dtoList){

            Date setStartTime=performancePlanShowDto.getSetStartTime();
            if (setStartTime==null){
                resultList.add(performancePlanShowDto);
            }
        }
        return resultList;
    }

    @Override
    public List<PerformancePlanShowDto> getSetTimeTask(PerformanceResultDto performanceResultDto) {
        List<PerformancePlanShow> list=performanceResultMapper.getTask(performanceResultDto);
        List<PerformancePlanShowDto> dtoList=CollectionUtils.transform(list, PerformancePlanShowDto.class);
        List<PerformancePlanShowDto> resultList=new ArrayList<PerformancePlanShowDto>();
        for(PerformancePlanShowDto performancePlanShowDto:dtoList){

            Date setStartTime=performancePlanShowDto.getSetStartTime();
            if (setStartTime!=null){
                resultList.add(performancePlanShowDto);
            }
        }
        return resultList;
    }

}
