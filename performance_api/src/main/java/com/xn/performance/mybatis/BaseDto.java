package com.xn.performance.mybatis;/**
 * Created by xn056839 on 2016/12/22.
 */

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 * @author zhouxi
 */
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 4728263072475016335L;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}