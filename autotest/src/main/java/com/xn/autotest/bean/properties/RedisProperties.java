package com.xn.autotest.bean.properties;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisProperties {
    private static final Logger logger = LoggerFactory.getLogger(RedisProperties.class);
    private String redisName;

    /**
     * 连redis的host和port，用host:port表示,多个用，隔开
     */
    private String redisHostAndPort;
    private  String timeout;
    /**
     * redis重定向次数
     */
    private  String redirections;


    public RedisProperties(String redisName, String redisHostAndPort, String timeout, String redirections) {
        this.redisName = redisName;
        this.redisHostAndPort = redisHostAndPort;
        this.timeout = timeout;
        this.redirections = redirections;
    }

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public String getRedisHostAndPort() {
        return redisHostAndPort;
    }

    public void setRedisHostAndPort(String redisHostAndPort) {
        this.redisHostAndPort = redisHostAndPort;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getRedirections() {
        return redirections;
    }

    public void setRedirections(String redirections) {
        this.redirections = redirections;
    }

}
