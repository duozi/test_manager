package com.xn.autotest.bean.properties;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DubboProperties {
    private static final Logger logger = LoggerFactory.getLogger(DubboProperties.class);
    private String appName;
    private String interfaceName;
    private String url;
    private String groupName;
    private String version;
    private String timeout;
    /**
     * 是否使用zk,1表示使用，0表示不使用（使用url直接调用）
     */
    private int useZk;
    private String zk;

    public DubboProperties(String appName, String interfaceName, String url, String groupName, String version, String timeout, int useZk, String zk) {
        this.appName = appName;
        this.interfaceName = interfaceName;
        this.url = url;
        this.groupName = groupName;
        this.version = version;
        this.timeout = timeout;
        this.useZk = useZk;
        this.zk = zk;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public int getUseZk() {
        return useZk;
    }

    public void setUseZk(int useZk) {
        this.useZk = useZk;
    }

    public String getZk() {
        return zk;
    }

    public void setZk(String zk) {
        this.zk = zk;
    }
}
