package com.xn.interfacetest.service;/**
 * Created by xn056839 on 2016/10/31.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLClassLoader;

public class GetPara {
    private static final Logger logger = LoggerFactory.getLogger(GetPara.class);


    private static URLClassLoader loader = null;
    private static String path;
    private static String remark;
    private static String system;

    public static String getRemark() {
        return remark;
    }

    public static void setRemark(String remark) {
        GetPara.remark = remark;
    }

    public static String getSystem() {
        return system;
    }

    public static void setSystem(String system) {
        GetPara.system = system;
    }

    public URLClassLoader getLoader() {
        return loader;
    }

    public void setLoader(URLClassLoader loader) {
        this.loader = loader;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
