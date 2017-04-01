/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestDatabaseConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * TestDatabaseConfig Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestDatabaseConfigMapper extends BaseMapper<TestDatabaseConfig, Long> {

    TestDatabaseConfig getByEnvironmentAndDbName(@Param("databaseName") String databaseName, @Param("environmentId") Long environmentId);
}
