/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.api.PerformanceScriptService;
import com.xn.performance.dao.PerformanceScriptMapper;
import com.xn.performance.dto.PerformanceScriptDto;
import com.xn.performance.entity.PerformanceScript;
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
 * PerformanceScript Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceScriptServiceImpl implements PerformanceScriptService {

    /**
     *  Dao
     */
    @Autowired
    private PerformanceScriptMapper performanceScriptMapper;

    @Override
    public PerformanceScriptDto get(Object condition)  
	{  
        PerformanceScript performanceScript = performanceScriptMapper.get(condition);
        PerformanceScriptDto performanceScriptDto = BeanUtils.toBean(performanceScript,PerformanceScriptDto.class);
	    return performanceScriptDto;  
	}  

    @Override
    public long count(PerformanceScriptDto condition) {
        return performanceScriptMapper.count(condition);
    }

    @Override
    public List<PerformanceScriptDto> list(PerformanceScriptDto condition) {
        List<PerformanceScript> list = performanceScriptMapper.list(condition);
        List<PerformanceScriptDto> dtoList = CollectionUtils.transform(list, PerformanceScriptDto.class);
        return dtoList;
    }
    @Override
    public PageResult<PerformanceScriptDto> listByPage(PerformanceScriptDto condition,PageInfo pageInfo) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Map beanMap=convertBean(condition);
        beanMap.put("page",pageInfo);
        List<PerformanceScript> list = performanceScriptMapper.listByPage(beanMap);
        List<PerformanceScriptDto> dtoList = CollectionUtils.transform(list, PerformanceScriptDto.class);
        return  PageResult.wrap(pageInfo, dtoList);
    }

    @Override
    public List<PerformanceScriptDto> list(Map<String,Object> condition) {
        List<PerformanceScript> list = performanceScriptMapper.list(condition);
        List<PerformanceScriptDto> dtoList = CollectionUtils.transform(list, PerformanceScriptDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformanceScriptDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceScriptDto save(PerformanceScriptDto performanceScriptDto) {
        PerformanceScript performanceScript = BeanUtils.toBean(performanceScriptDto,PerformanceScript.class);
        performanceScriptMapper.save(performanceScript);
        performanceScriptDto.setId(performanceScript.getId());
        return performanceScriptDto;
    }

    @Override
    public int save(List<PerformanceScriptDto> performanceScriptDtos) {
        if (performanceScriptDtos == null || performanceScriptDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceScript> performanceScripts = CollectionUtils.transform(performanceScriptDtos, PerformanceScript.class);
        return performanceScriptMapper.saveBatch(performanceScripts);
    }

    @Override
    public int update(PerformanceScriptDto performanceScriptDto) {
        PerformanceScript performanceScript = BeanUtils.toBean(performanceScriptDto,PerformanceScript.class);
        return performanceScriptMapper.update(performanceScript);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performanceScriptMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceScriptDto performanceScriptDto) {
        PerformanceScript performanceScript = BeanUtils.toBean(performanceScriptDto,PerformanceScript.class);
        return performanceScriptMapper.delete(performanceScript);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceScriptMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformanceScriptDto> performanceScripts) {
        return 0;
    }

}
