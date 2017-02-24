package com.xn.manage.bean;/**
 * Created by xn056839 on 2016/12/22.
 */

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class BaseEntity implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
