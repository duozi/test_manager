package com.xn.autotest.bean.resultBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertDiff {
    private static final Logger logger = LoggerFactory.getLogger(AssertDiff.class);
    /**
     * 断言类型，1 返回断言，2 数据库断言，3 redisProperties 断言
     */
    private int assertType;
    /**
     * db或者是redis的名字，如果是参数校验就是parameter
     */
    private String name;

    private  String assertKey;
    private  String expectVaule;
    private  String exactValue;

    public AssertDiff(int assertType, String name, String assertKey, String expectVaule, String exactValue) {
        this.assertType = assertType;
        this.name = name;
        this.assertKey = assertKey;
        this.expectVaule = expectVaule;
        this.exactValue = exactValue;
    }

    public int getAssertType() {
        return assertType;
    }

    public void setAssertType(int assertType) {
        this.assertType = assertType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssertKey() {
        return assertKey;
    }

    public void setAssertKey(String assertKey) {
        this.assertKey = assertKey;
    }

    public String getExpectVaule() {
        return expectVaule;
    }

    public void setExpectVaule(String expectVaule) {
        this.expectVaule = expectVaule;
    }

    public String getExactValue() {
        return exactValue;
    }

    public void setExactValue(String exactValue) {
        this.exactValue = exactValue;
    }
}
