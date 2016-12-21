package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodBean {
    private static final Logger logger = LoggerFactory.getLogger(MethodBean.class);
    private String methodName;

    public MethodBean(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
