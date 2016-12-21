package com.xn.autotest.bean.assertBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisAssert {
    private static final Logger logger = LoggerFactory.getLogger(RedisAssert.class);
    private String redisName;
    private String redisKey;
    /**
     * redis校验内容，用json表示
     */
    private String redisAsserContent;

    public RedisAssert(String redisName, String redisKey, String redisAsserContent) {
        this.redisName = redisName;
        this.redisKey = redisKey;
        this.redisAsserContent = redisAsserContent;
    }

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getRedisAsserContent() {
        return redisAsserContent;
    }

    public void setRedisAsserContent(String redisAsserContent) {
        this.redisAsserContent = redisAsserContent;
    }
}
