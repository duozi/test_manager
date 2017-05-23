/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.api.PerformanceScenarioService;
import com.xn.performance.dao.PerformanceScenarioMapper;
import com.xn.performance.dto.PerformanceScenarioDto;
import com.xn.performance.entity.PerformanceScenario;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.xn.performance.util.BeanToMapUtil.convertBean;


/**
 * PerformanceScenario Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceScenarioServiceImpl implements PerformanceScenarioService {

    /**
     *  Dao
     */
    @Autowired
    private PerformanceScenarioMapper performanceScenarioMapper;

    @Override
    public PerformanceScenarioDto get(Object condition)  
	{  
        PerformanceScenario performanceScenario = performanceScenarioMapper.get(condition);
        PerformanceScenarioDto performanceScenarioDto = BeanUtils.toBean(performanceScenario,PerformanceScenarioDto.class);
	    return performanceScenarioDto;  
	}  

    @Override
    public long count(PerformanceScenarioDto condition) {
        return performanceScenarioMapper.count(condition);
    }

    @Override
    public List<PerformanceScenarioDto> list(PerformanceScenarioDto condition) {
        List<PerformanceScenario> list = performanceScenarioMapper.list(condition);
        List<PerformanceScenarioDto> dtoList = CollectionUtils.transform(list, PerformanceScenarioDto.class);
        return dtoList;
    }
    @Override
    public PageResult<PerformanceScenarioDto> listByPage(PerformanceScenarioDto condition,PageInfo pageInfo) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map beanMap=convertBean(condition);
        beanMap.put("page",pageInfo);
        List<PerformanceScenario> list = performanceScenarioMapper.listByPage(beanMap);
        List<PerformanceScenarioDto> dtoList = CollectionUtils.transform(list, PerformanceScenarioDto.class);
        return PageResult.wrap(pageInfo, dtoList);
    }

    @Override
    public List<PerformanceScenarioDto> list(Map<String,Object> condition) {
        List<PerformanceScenario> list = performanceScenarioMapper.list(condition);
        List<PerformanceScenarioDto> dtoList = CollectionUtils.transform(list, PerformanceScenarioDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformanceScenarioDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceScenarioDto save(PerformanceScenarioDto performanceScenarioDto) {
        PerformanceScenario performanceScenario = BeanUtils.toBean(performanceScenarioDto,PerformanceScenario.class);
        performanceScenarioMapper.save(performanceScenario);
        performanceScenarioDto.setId(performanceScenario.getId());
        return performanceScenarioDto;
    }

    @Override
    public int save(List<PerformanceScenarioDto> performanceScenarioDtos) {
        if (performanceScenarioDtos == null || performanceScenarioDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceScenario> performanceScenarios = CollectionUtils.transform(performanceScenarioDtos, PerformanceScenario.class);
        return performanceScenarioMapper.saveBatch(performanceScenarios);
    }

    @Override
    public int update(PerformanceScenarioDto performanceScenarioDto) {
        PerformanceScenario performanceScenario = BeanUtils.toBean(performanceScenarioDto,PerformanceScenario.class);
        return performanceScenarioMapper.update(performanceScenario);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performanceScenarioMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceScenarioDto performanceScenarioDto) {
        PerformanceScenario performanceScenario = BeanUtils.toBean(performanceScenarioDto,PerformanceScenario.class);
        return performanceScenarioMapper.delete(performanceScenario);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceScenarioMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformanceScenarioDto> performanceScenarios) {
        return 0;
    }

}
