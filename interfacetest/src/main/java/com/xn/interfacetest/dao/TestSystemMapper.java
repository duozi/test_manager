/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;



import java.util.List;
import java.util.Map;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.TestSystem;
import org.springframework.stereotype.Service;

/**
 * TestSystem Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestSystemMapper extends BaseMapper<TestSystem, Long> {
    /** 按条件查询对象 **/
    List<TestSystem> listByCompany(Map<String, Object> parameters);

    List<TestSystem> getWithCompanyInfoBySystems(String[] ids);

    TestSystem getByName(String name);
}
