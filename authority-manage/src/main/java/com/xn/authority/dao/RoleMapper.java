/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dao;


import com.xn.authority.entity.Role;
import com.xn.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 角色表 Dao 接口
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface RoleMapper extends BaseMapper<Role, Long> {

    List< Role> listAllRoleByUserId(Map<String, Object> condition);
}
