/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestEnvironment;

import java.util.List;

/**
 * TestEnvironment Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestEnvironmentMapper extends BaseMapper<TestEnvironment, Long> {

    List<TestEnvironment> getByPlanId(Long planId);
}
