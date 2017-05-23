/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.dao;

import com.xn.performance.entity.PerformancePlanShow;
import com.xn.performance.entity.PerformanceResult;
import com.xn.performance.mybatis.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * PerformanceResult Dao 接口
 *
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public interface PerformanceResultMapper extends BaseMapper<PerformanceResult, Integer> {
    List<PerformancePlanShow> getNowTask(Object condition);

    List<PerformancePlanShow> getScheduleTask(Object condition);

    List<PerformancePlanShow> resultList(Map<String, Object> parameters);

    PerformancePlanShow getShow(Object condition);


}
