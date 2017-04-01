/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TimeConfig;

import java.util.List;

/**
 * TimeConfig Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TimeConfigMapper extends BaseMapper<TimeConfig, Long> {

    List<TimeConfig> getByPlanId(Long planId);

    void deleteByPlanId(Long planId);
}
