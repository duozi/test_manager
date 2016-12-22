package com.xn.manage.entity;

import com.alibaba.fastjson.JSON;

public class BaseEntity {
	   @Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }
}
