package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseBean {
    private static final Logger logger = LoggerFactory.getLogger(CaseBean.class);
    private String caseName;
    private String param;

    public CaseBean(String caseName, String param) {
        this.caseName = caseName;
        this.param = param;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
