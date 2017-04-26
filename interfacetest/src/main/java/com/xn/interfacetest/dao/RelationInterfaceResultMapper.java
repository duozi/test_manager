/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import java.util.List;
import java.util.Map;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationInterfaceResult;
import org.springframework.stereotype.Service;

/**
 * RelationInterfaceResult Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface RelationInterfaceResultMapper extends BaseMapper<RelationInterfaceResult, Long> {

    List<RelationInterfaceResult> getByParams(Map<String, Object> params);
}
