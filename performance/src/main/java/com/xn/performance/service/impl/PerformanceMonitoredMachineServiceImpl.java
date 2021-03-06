/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.api.PerformanceMonitoredMachineService;
import com.xn.performance.dao.PerformanceMonitoredMachineMapper;
import com.xn.performance.dto.PerformanceMonitoredMachineDto;
import com.xn.performance.entity.PerformanceMonitoredMachine;
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
import static com.xn.performance.util.jmeter.StartJMeterAgent_SSH.test_link;


/**
 * PerformanceMonitoredMachine Service实现
 *
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceMonitoredMachineServiceImpl implements PerformanceMonitoredMachineService {

    /**
     * Dao
     */
    @Autowired
    private PerformanceMonitoredMachineMapper performanceMonitoredMachineMapper;

    @Override
    public PerformanceMonitoredMachineDto get(Object condition) {
        PerformanceMonitoredMachine performanceMonitoredMachine = performanceMonitoredMachineMapper.get(condition);
        PerformanceMonitoredMachineDto performanceMonitoredMachineDto = BeanUtils.toBean(performanceMonitoredMachine, PerformanceMonitoredMachineDto.class);
        return performanceMonitoredMachineDto;
    }

    @Override
    public long count(PerformanceMonitoredMachineDto condition) {
        return performanceMonitoredMachineMapper.count(condition);
    }

    @Override
    public List<PerformanceMonitoredMachineDto> list(PerformanceMonitoredMachineDto condition) {
        List<PerformanceMonitoredMachine> list = performanceMonitoredMachineMapper.list(condition);
        List<PerformanceMonitoredMachineDto> dtoList = CollectionUtils.transform(list, PerformanceMonitoredMachineDto.class);
        return dtoList;
    }
    @Override
    public PageResult<PerformanceMonitoredMachineDto> listByPage(PerformanceMonitoredMachineDto condition,PageInfo pageInfo) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Map beanMap=convertBean(condition);
        beanMap.put("page",pageInfo);
        List<PerformanceMonitoredMachine> list = performanceMonitoredMachineMapper.listByPage(beanMap);
        List<PerformanceMonitoredMachineDto> dtoList = CollectionUtils.transform(list, PerformanceMonitoredMachineDto.class);
        return PageResult.wrap(pageInfo, dtoList);
    }

    @Override
    public List<PerformanceMonitoredMachineDto> list(Map<String, Object> condition) {
        List<PerformanceMonitoredMachine> list = performanceMonitoredMachineMapper.list(condition);
        List<PerformanceMonitoredMachineDto> dtoList = CollectionUtils.transform(list, PerformanceMonitoredMachineDto.class);
        return dtoList;
    }

    @Override
    public PageResult<PerformanceMonitoredMachineDto> page(Map<String, Object> condition) {
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceMonitoredMachineDto save(PerformanceMonitoredMachineDto performanceMonitoredMachineDto) {
        PerformanceMonitoredMachine performanceMonitoredMachine = BeanUtils.toBean(performanceMonitoredMachineDto, PerformanceMonitoredMachine.class);
        performanceMonitoredMachineMapper.save(performanceMonitoredMachine);
        performanceMonitoredMachineDto.setId(performanceMonitoredMachine.getId());
        return performanceMonitoredMachineDto;
    }

    @Override
    public int save(List<PerformanceMonitoredMachineDto> performanceMonitoredMachineDtos) {
        if (performanceMonitoredMachineDtos == null || performanceMonitoredMachineDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceMonitoredMachine> performanceMonitoredMachines = CollectionUtils.transform(performanceMonitoredMachineDtos, PerformanceMonitoredMachine.class);
        return performanceMonitoredMachineMapper.saveBatch(performanceMonitoredMachines);
    }

    @Override
    public int update(PerformanceMonitoredMachineDto performanceMonitoredMachineDto) {
        PerformanceMonitoredMachine performanceMonitoredMachine = BeanUtils.toBean(performanceMonitoredMachineDto, PerformanceMonitoredMachine.class);
        return performanceMonitoredMachineMapper.update(performanceMonitoredMachine);
    }

    @Override
    public int deleteByPK(Integer id) {
        return performanceMonitoredMachineMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceMonitoredMachineDto performanceMonitoredMachineDto) {
        PerformanceMonitoredMachine performanceMonitoredMachine = BeanUtils.toBean(performanceMonitoredMachineDto, PerformanceMonitoredMachine.class);
        return performanceMonitoredMachineMapper.delete(performanceMonitoredMachine);
    }

    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceMonitoredMachineMapper.deleteBatchByPK(ids);
    }

    @Override
    public int deleteBatch(List<PerformanceMonitoredMachineDto> performanceMonitoredMachines) {
        return 0;
    }

    @Override
    public boolean testLink(String ip, String username, String password,int port) {
        return test_link(ip,username,password,port);
    }
}
