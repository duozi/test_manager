package com.xn.autotest.bean.operation;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisOperation {
    private static final Logger logger = LoggerFactory.getLogger(RedisOperation.class);
    private String redisName;
    /**
     * redis操作类型，1 beforeclass 2 afterclass 3 before ,4 after
     */
    private int type;
    /**
     * 执行顺序 从1开始
     */
    private int operationOrder;
    private String redisKey;
    private String redisValue;
    private String redisTime;
    /**
     * 执行redis 操作类型，1 set，2 del 3 settime
     */
    private int actionType;

    public RedisOperation(String redisName, int type, int operationOrder, String redisKey, String redisValue, String redisTime, int actionType) {
        this.redisName = redisName;
        this.type = type;
        this.operationOrder = operationOrder;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.redisTime = redisTime;
        this.actionType = actionType;
    }

    public String getRedisName() {
        return redisName;
    }

    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOperationOrder() {
        return operationOrder;
    }

    public void setOperationOrder(int operationOrder) {
        this.operationOrder = operationOrder;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getRedisValue() {
        return redisValue;
    }

    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }

    public String getRedisTime() {
        return redisTime;
    }

    public void setRedisTime(String redisTime) {
        this.redisTime = redisTime;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
}
