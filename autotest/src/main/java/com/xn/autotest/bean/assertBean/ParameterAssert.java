package com.xn.autotest.bean.assertBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterAssert {
    private static final Logger logger = LoggerFactory.getLogger(ParameterAssert.class);
    private String parameterAssertContent;

    public ParameterAssert(String parameterAssertContent) {
        this.parameterAssertContent = parameterAssertContent;
    }

    public String getParameterAssertContent() {
        return parameterAssertContent;
    }

    public void setParameterAssertContent(String parameterAssertContent) {
        this.parameterAssertContent = parameterAssertContent;
    }
}
