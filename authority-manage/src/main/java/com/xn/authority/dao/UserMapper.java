/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.dao;


import com.xn.authority.dto.UserDto;
import com.xn.authority.entity.User;
import com.xn.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 用户表 Dao 接口
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface UserMapper extends BaseMapper<User, Long> {

    /**
     * 根据名称查询用户
     * @param condition
     * @return
     */
    List<User> findByAccount(Map<String, Object> condition);
}
