/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import java.util.List;
import java.util.Map;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestService;
import org.springframework.stereotype.Service;

/**
 * TestService Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestServiceMapper extends BaseMapper<TestService, Long> {

    List<TestService> listByParams(Map<String, Object> params);

    TestService getByName(String name);
}
