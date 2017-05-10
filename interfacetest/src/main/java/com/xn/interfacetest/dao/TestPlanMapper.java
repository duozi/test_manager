/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * TestPlan Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestPlanMapper extends BaseMapper<TestPlan, Long> {

    void updateStatus(@Param("status") Integer status, @Param("id") Long id);
}
