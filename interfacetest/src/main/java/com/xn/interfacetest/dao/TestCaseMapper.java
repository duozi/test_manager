/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestCase;

import java.util.List;
import java.util.Map;


/**
 * TestCase Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */

public interface TestCaseMapper extends BaseMapper<TestCase, Long> {

    int updatePart(TestCase testCase);

    List<TestCase> listByParams(Map<String, Object> params);
}
