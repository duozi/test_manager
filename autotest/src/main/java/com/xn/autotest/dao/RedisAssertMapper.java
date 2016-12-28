/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.dao;


import com.xn.autotest.bean.assertBean.redisAssert.entity.RedisAssert;
import com.xn.autotest.mybatis.BaseMapper;
import org.springframework.stereotype.Service;

/**
 * RedisAssert Dao 接口
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public interface RedisAssertMapper extends BaseMapper<RedisAssert, Integer> {

}
