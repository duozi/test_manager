/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.dao.PerformancePlanMonitoredMapper;
import com.xn.performance.dto.PerformancePlanMonitoredDto;
import com.xn.performance.entity.PerformancePlanMonitored;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.service.PerformancePlanMonitoredService;
import com.xn.performance.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xn.performance.util.CollectionUtils;
import java.util.List;
import java.util.Map;


/**
 * PerformancePlanMonitored Service实现
 * 
 * @author zhouxi
 * @date 2017-03-01
 */
@Service
public class PerformancePlanMonitoredServiceImpl implements PerformancePlanMonitoredService {

    /**
     *  Dao
     */
    @Autowired
    private PerformancePlanMonitoredMapper performancePlanMonitoredMapper;

    @Override
    public PerformancePlanMonitoredDto get(Object condition)  
	{  
        PerformancePlanMonitored performancePlanMonitored = performancePlanMonitoredMapper.get(condition);
        PerformancePlanMonitoredDto performancePlanMonitoredDto = BeanUtils.toBean(performancePlanMonitored,PerformancePlanMonitoredDto.class);
	    return performancePlanMonitoredDto;  
	}  

    @Override
    public long count(PerformancePlanMonitoredDto condition) {
        return performancePlanMonitoredMapper.count(condition);
    }

    @Override
    public List<PerformancePlanMonitoredDto> list(PerformancePlanMonitoredDto condition) {
        List<PerformancePlanMonitored> list = performancePlanMonitoredMapper.list(condition);
        List<PerformancePlanMonitoredDto> dtoList = CollectionUtils.transform(list, PerformancePlanMonitoredDto.class);
        return dtoList;
    }

    @Override
    public List<PerformancePlanMonitoredDto> list(Map<String,Object> condition) {
        List<PerformancePlanMonitored> list = performancePlanMonitoredMapper.list(condition);
        List<PerformancePlanMonitoredDto> dtoList = CollectionUtils.transform(list, PerformancePlanMonitoredDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformancePlanMonitoredDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformancePlanMonitoredDto save(PerformancePlanMonitoredDto performancePlanMonitoredDto) {
        PerformancePlanMonitored performancePlanMonitored = BeanUtils.toBean(performancePlanMonitoredDto,PerformancePlanMonitored.class);
        performancePlanMonitoredMapper.save(performancePlanMonitored);
        performancePlanMonitoredDto.setId(performancePlanMonitored.getId());
        return performancePlanMonitoredDto;
    }

    @Override
    public int save(List<PerformancePlanMonitoredDto> performancePlanMonitoredDtos) {
        if (performancePlanMonitoredDtos == null || performancePlanMonitoredDtos.isEmpty()) {
            return 0;
        }
        List<PerformancePlanMonitored> performancePlanMonitoreds = CollectionUtils.transform(performancePlanMonitoredDtos, PerformancePlanMonitored.class);
        return performancePlanMonitoredMapper.saveBatch(performancePlanMonitoreds);
    }

    @Override
    public int update(PerformancePlanMonitoredDto performancePlanMonitoredDto) {
        PerformancePlanMonitored performancePlanMonitored = BeanUtils.toBean(performancePlanMonitoredDto,PerformancePlanMonitored.class);
        return performancePlanMonitoredMapper.update(performancePlanMonitored);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performancePlanMonitoredMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformancePlanMonitoredDto performancePlanMonitoredDto) {
        PerformancePlanMonitored performancePlanMonitored = BeanUtils.toBean(performancePlanMonitoredDto,PerformancePlanMonitored.class);
        return performancePlanMonitoredMapper.delete(performancePlanMonitored);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performancePlanMonitoredMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformancePlanMonitoredDto> performancePlanMonitoreds) {
        return 0;
    }

}
