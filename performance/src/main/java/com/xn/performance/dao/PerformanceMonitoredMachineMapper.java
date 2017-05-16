/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dao;

import com.xn.performance.entity.PerformanceMonitoredMachine;
import com.xn.performance.mybatis.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * PerformanceMonitoredMachine Dao 接口
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public interface PerformanceMonitoredMachineMapper extends BaseMapper<PerformanceMonitoredMachine, Integer> {
    List<PerformanceMonitoredMachine> listByPage(Map<String, Object> parameters);
}
