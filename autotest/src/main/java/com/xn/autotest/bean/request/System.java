package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {
    private static final Logger logger = LoggerFactory.getLogger(System.class);
   public enum typeEnum{
        DUBBO,
        HTTP
    }
    /**
     *系统名称
     */
    private String systemName;
    /**
     *系统的类型,0表示http,1表示dubbo
     */
    private typeEnum type;

}
