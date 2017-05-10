/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationPlanEnvironment;
import org.springframework.stereotype.Service;

/**
 * RelationPlanEnvironment Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface RelationPlanEnvironmentMapper extends BaseMapper<RelationPlanEnvironment, Long> {

    void deleteByPlanId(Long planId);

    RelationPlanEnvironment getByPlanId(Long planId);
}
