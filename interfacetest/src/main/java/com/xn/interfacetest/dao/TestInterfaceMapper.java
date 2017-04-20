/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import java.util.List;
import java.util.Map;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestInterface;
import org.springframework.stereotype.Service;

/**
 * TestInterface Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestInterfaceMapper extends BaseMapper<TestInterface, Long> {

    List<TestInterface> listByParams(Map<String, Object> params);

    String getParamsByInterfaceId(String interfaceId);

    TestInterface getByCaseId(Long caseId);

    List<TestInterface> listWithInfoByIds(String[] interfaceArray);
}
