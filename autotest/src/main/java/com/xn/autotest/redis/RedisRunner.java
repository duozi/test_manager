package com.xn.autotest.redis;/**
 * Created by xn056839 on 2016/12/28.
 */

import com.xn.autotest.bean.operation.redisOperation.dto.RedisOperationDto;
import com.xn.autotest.enums.RedisOperationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisRunner {
    private static final Logger logger = LoggerFactory.getLogger(RedisRunner.class);
    @Resource
    PooledRedisSource pooledRedisSource;

    public List<Map<String, Object>> excute(RedisOperationDto redisOperationDto) throws Exception {
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = pooledRedisSource.getJedisCluster(redisOperationDto.getRedisName());
            return excute(jedisCluster, redisOperationDto);
        } finally {
            jedisCluster.close();
        }
    }

    private List<Map<String, Object>> excute(JedisCluster jedisCluster, RedisOperationDto redisOperationDto) {
        RedisOperationEnum redisOperationEnum = redisOperationDto.getActionType();
        if (redisOperationEnum.getType() == RedisOperationEnum.SET.getType()) {
            return set(jedisCluster, redisOperationDto.getRedisKey(), redisOperationDto.getRedisValue(), redisOperationDto.getRedisTime());


        } else if (redisOperationEnum.getType() == RedisOperationEnum.GET.getType()) {
            return get(jedisCluster, redisOperationDto.getRedisKey());

        } else if (redisOperationEnum.getType() == RedisOperationEnum.DEL.getType()) {
            return del(jedisCluster, redisOperationDto.getRedisKey());
        } else if (redisOperationEnum.getType() == RedisOperationEnum.EXPIRE.getType()) {
            return setTime(jedisCluster, redisOperationDto.getRedisKey(), redisOperationDto.getRedisTime());
        } else {
            return null;
        }

    }

    public List<Map<String, Object>> set(JedisCluster jedisCluster, String key, String value, int time) {
        Map<String, Object> temp = new HashMap<String, Object>();

        temp.put("setResult", jedisCluster.set(key, value));
        if (time != 0) {

            temp.put("setTime", jedisCluster.expire(key, time));
            logger.info("set to redis key:{} value:{},limit time is " + time + " seconds", key, value);
        } else {

            logger.info("set to redis key:{} value:{},limit time is forever", key, value);

        }
        return Arrays.asList(temp);
    }

    public List<Map<String, Object>> del(JedisCluster jedisCluster, String key) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("delResult", jedisCluster.del(key));

        logger.info("del from redis key:{}", key);
        return Arrays.asList(temp);
    }

    public List<Map<String, Object>> get(JedisCluster jedisCluster, String key) {
        Map<String, Object> temp = new HashMap<String, Object>();
        String value = jedisCluster.get(key);
        temp.put("getResult", value);
        logger.info("get value from redis,key:{},value:{}", key, value);
        return Arrays.asList(temp);
    }

    public List<Map<String, Object>> setTime(JedisCluster jedisCluster, String key, int expire) {
        Map<String, Object> temp = new HashMap<String, Object>();

        Long result = jedisCluster.expire(key, expire);
        temp.put("setTimeResult", result);
        logger.info("set expire ,key:{},exprie:{}second", key, expire);
        return Arrays.asList(temp);
    }


}
