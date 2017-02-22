/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.dao.PerformanceMonitoredMachineResultMapper;
import com.xn.performance.dto.PerformanceMonitoredMachineResultDto;
import com.xn.performance.entity.PerformanceMonitoredMachineResult;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.service.PerformanceMonitoredMachineResultService;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xn.performance.mybatis.PageResult;

import java.util.List;
import java.util.Map;


/**
 * PerformanceMonitoredMachineResult Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceMonitoredMachineResultServiceImpl implements PerformanceMonitoredMachineResultService {

    /**
     *  Dao
     */
    @Autowired
    private PerformanceMonitoredMachineResultMapper performanceMonitoredMachineResultMapper;

    @Override
    public PerformanceMonitoredMachineResultDto get(Object condition)  
	{  
        PerformanceMonitoredMachineResult performanceMonitoredMachineResult = performanceMonitoredMachineResultMapper.get(condition);
        PerformanceMonitoredMachineResultDto performanceMonitoredMachineResultDto = BeanUtils.toBean(performanceMonitoredMachineResult,PerformanceMonitoredMachineResultDto.class);
	    return performanceMonitoredMachineResultDto;  
	}  

    @Override
    public long count(PerformanceMonitoredMachineResultDto condition) {
        return performanceMonitoredMachineResultMapper.count(condition);
    }

    @Override
    public List<PerformanceMonitoredMachineResultDto> list(PerformanceMonitoredMachineResultDto condition) {
        List<PerformanceMonitoredMachineResult> list = performanceMonitoredMachineResultMapper.list(condition);
        List<PerformanceMonitoredMachineResultDto> dtoList = CollectionUtils.transform(list, PerformanceMonitoredMachineResultDto.class);
        return dtoList;
    }

    @Override
    public List<PerformanceMonitoredMachineResultDto> list(Map<String,Object> condition) {
        List<PerformanceMonitoredMachineResult> list = performanceMonitoredMachineResultMapper.list(condition);
        List<PerformanceMonitoredMachineResultDto> dtoList = CollectionUtils.transform(list, PerformanceMonitoredMachineResultDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformanceMonitoredMachineResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceMonitoredMachineResultDto save(PerformanceMonitoredMachineResultDto performanceMonitoredMachineResultDto) {
        PerformanceMonitoredMachineResult performanceMonitoredMachineResult = BeanUtils.toBean(performanceMonitoredMachineResultDto,PerformanceMonitoredMachineResult.class);
        performanceMonitoredMachineResultMapper.save(performanceMonitoredMachineResult);
        performanceMonitoredMachineResultDto.setId(performanceMonitoredMachineResult.getId());
        return performanceMonitoredMachineResultDto;
    }

    @Override
    public int save(List<PerformanceMonitoredMachineResultDto> performanceMonitoredMachineResultDtos) {
        if (performanceMonitoredMachineResultDtos == null || performanceMonitoredMachineResultDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceMonitoredMachineResult> performanceMonitoredMachineResults = CollectionUtils.transform(performanceMonitoredMachineResultDtos, PerformanceMonitoredMachineResult.class);
        return performanceMonitoredMachineResultMapper.saveBatch(performanceMonitoredMachineResults);
    }

    @Override
    public int update(PerformanceMonitoredMachineResultDto performanceMonitoredMachineResultDto) {
        PerformanceMonitoredMachineResult performanceMonitoredMachineResult = BeanUtils.toBean(performanceMonitoredMachineResultDto,PerformanceMonitoredMachineResult.class);
        return performanceMonitoredMachineResultMapper.update(performanceMonitoredMachineResult);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performanceMonitoredMachineResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceMonitoredMachineResultDto performanceMonitoredMachineResultDto) {
        PerformanceMonitoredMachineResult performanceMonitoredMachineResult = BeanUtils.toBean(performanceMonitoredMachineResultDto,PerformanceMonitoredMachineResult.class);
        return performanceMonitoredMachineResultMapper.delete(performanceMonitoredMachineResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceMonitoredMachineResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformanceMonitoredMachineResultDto> performanceMonitoredMachineResults) {
        return 0;
    }

}
