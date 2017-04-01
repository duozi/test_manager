/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.ParamEntity;
import com.xn.interfacetest.entity.TestParams;

import java.util.List;

/**
 * TestParams Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
public interface TestParamsMapper extends BaseMapper<TestParams, Long> {

    List<TestParams> getParamsByInterfaceId(String interfaceId);

    List<ParamEntity> listByCaseIdFromRelation(Long caseId);
}
