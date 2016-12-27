/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.dao;

import com.xn.autotest.bean.request.plan.entity.Plan;
import com.xn.autotest.mybatis.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * Plan Dao 接口
 *
 * @author xn056839
 * @date 2016-12-22
 */
@Repository("planMapper")
public interface PlanMapper extends BaseMapper<Plan, Integer> {

}
