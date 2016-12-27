package com.xn.autotest.dao.ago;

import com.xn.autotest.bean.properties.redisProperties.entity.RedisProperties;

import java.util.List;

/**
 * Created by xn056839 on 2016/12/23.
 */
public interface RedisPropertiesDao {
    List<RedisProperties> getAllRedisProperties();
}
