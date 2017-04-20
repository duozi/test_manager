package com.xn.common.base;/**
 * Created by xn056839 on 2016/12/22.
 */

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BaseEntity  implements Serializable {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
