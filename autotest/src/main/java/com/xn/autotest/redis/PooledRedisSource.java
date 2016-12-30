package com.xn.autotest.redis;/**
 * Created by xn056839 on 2016/12/23.
 */

import com.google.common.collect.Sets;
import com.xn.autotest.bean.properties.redisProperties.dto.RedisPropertiesDto;
import com.xn.autotest.bean.properties.redisProperties.service.RedisPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.*;

@Service
public class PooledRedisSource implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(PooledRedisSource.class);
    @Autowired
    RedisPropertiesService redisPropertiesService;

    private final static Map<String, JedisCluster> REDISSOURCEWRAPPERS = new HashMap<>();
    private List<RedisPropertiesDto> redisPropertiesList = new ArrayList<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        List<RedisPropertiesDto> redisPropertiesList = redisPropertiesService.list();
        this.redisPropertiesList = redisPropertiesList;

    }

    private RedisPropertiesDto getRedisPropertiesByName(String RedisName) {
        if (redisPropertiesList.isEmpty()) {
            return null;
        } else {
            for (RedisPropertiesDto properties : redisPropertiesList) {
                if (properties.getRedisName().equals(RedisName)) {
                    return properties;
                }

            }
            return null;
        }
    }

    public JedisCluster getJedisCluster(String RedisName) {
        JedisCluster jedisCluster = REDISSOURCEWRAPPERS.get(RedisName);
        if (jedisCluster == null) {

            jedisCluster = createJedisCluster(RedisName);
            if (jedisCluster != null) {
                REDISSOURCEWRAPPERS.put(RedisName, jedisCluster);
            }
        }
        return jedisCluster;
    }

    private JedisCluster createJedisCluster(String RedisName) {

        RedisPropertiesDto redisProperties = getRedisPropertiesByName(RedisName);
        if (redisProperties == null) {
            return null;

        }
        String redisHostPort = redisProperties.getRedisHostPort();
        String[] hostAndPortArray = redisHostPort.split(",");

        Set<HostAndPort> nodes = Sets.newHashSet();
        for (String s : hostAndPortArray) {
            nodes.add(HostAndPort.parseString(s));
        }

        int timeout = redisProperties.getTimeout();
        int redirections = redisProperties.getRedirections();

        JedisCluster jedisCluster = new JedisCluster(nodes, timeout, redirections);


        return jedisCluster;
    }

    public void delRedisSource(String redisName) {
        REDISSOURCEWRAPPERS.remove(redisName);
        RedisPropertiesDto redisPropertiesDto = new RedisPropertiesDto();
        redisPropertiesDto.setRedisName(redisName);
        redisPropertiesService.delete(redisPropertiesDto);
    }


}
