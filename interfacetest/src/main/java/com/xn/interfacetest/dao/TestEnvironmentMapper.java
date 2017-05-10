/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import java.util.List;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestEnvironment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * TestEnvironment Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestEnvironmentMapper extends BaseMapper<TestEnvironment, Long> {

    List<TestEnvironment> getByPlanId(Long planId);

    void changeStatus(@Param("status")int status, @Param("id") Long id);
}
