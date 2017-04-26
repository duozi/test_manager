/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import org.apache.ibatis.annotations.Param;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestRedisConfig;
import org.springframework.stereotype.Service;

/**
 * TestRedisConfig Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestRedisConfigMapper extends BaseMapper<TestRedisConfig, Long> {

    TestRedisConfig getByRedisNameAndEnvironmentId(@Param("redisName") String redisName, @Param("environmentId") Long environmentId);
}
