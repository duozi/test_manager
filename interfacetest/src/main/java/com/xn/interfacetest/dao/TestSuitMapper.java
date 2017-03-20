/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestSuit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TestSuit Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestSuitMapper extends BaseMapper<TestSuit, Long> {

    List<TestSuit> getSuitByCaseId(@Param("caseId") Long caseId);
}
