/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestSuit;
import org.springframework.stereotype.Service;

/**
 * TestSuit Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestSuitMapper extends BaseMapper<TestSuit, Long> {

    List<TestSuit> getSuitByCaseId(@Param("caseId") Long caseId);

    List<TestSuit> getByPlanId(Long planId);

    void changeStatus(@Param("status")int status,@Param("id") Long id);
}
