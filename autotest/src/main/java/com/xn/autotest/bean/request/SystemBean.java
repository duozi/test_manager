package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemBean {
    private static final Logger logger = LoggerFactory.getLogger(System.class);

    /**
     *系统名称
     */
    private String systemName;
    /**
     *系统的类型,0表示http,1表示dubbo
     */
    private int type;

    public SystemBean(String systemName, int type) {
        this.systemName = systemName;
        this.type = type;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
