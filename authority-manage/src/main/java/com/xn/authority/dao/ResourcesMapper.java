/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dao;

import com.xn.authority.entity.Resources;
import com.xn.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 菜单资源表 Dao 接口
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface ResourcesMapper extends BaseMapper<Resources, Long> {

    /**
     * 根据角色列表查询所有权限
     * @param condition
     * @return
     */
    List< Resources> listAllResource(Map<String, Object> condition);
}
