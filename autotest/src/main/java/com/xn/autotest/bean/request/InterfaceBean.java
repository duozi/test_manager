package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterfaceBean {
    private static final Logger logger = LoggerFactory.getLogger(InterfaceBean.class);
    /**
     * 接口名称
     */
    private String InterfaceName;
    /**
     * 接口的类型,0表示http,1表示dubbo
     */
    private int type;

    public InterfaceBean(String interfaceName, int type) {
        InterfaceName = interfaceName;
        this.type = type;
    }

    public String getInterfaceName() {
        return InterfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        InterfaceName = interfaceName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
