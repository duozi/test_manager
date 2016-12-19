package com.xn.autotest.bean;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.xn.autotest.utils.ReflectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;


public class DubboProperties {
    private String interfaceName;

    private String methodName;

    private String url;

    private String version;

    private String group;
    private String timeout;
    private String appName;
    private String zk;
    private Boolean useZk;

    public Boolean getUseZk() {
        return useZk;
    }

    public void setUseZk(Boolean useZk) {
        this.useZk = useZk;
    }

    public String getZk() {
        return zk;
    }

    public DubboProperties() {
    }

    public DubboProperties(String interfaceName, String methodName, String url, String zk, String version, String group, String timeout, String appName, Boolean useZK) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.url = url;
        this.version = version;
        this.group = group;
        this.timeout = timeout;
        this.appName = appName;
        this.useZk=useZK;
        if(this.useZk){
            String[] strings=zk.split(",");
           List list= Lists.newArrayList(strings);
            Collections.sort(list);
           this.zk=StringUtils.join(list,",");
        }
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getVersion() {
        version = StringUtils.isBlank(this.version) ? ConfigUtils.getProperty("rpc.version", "1.0") : version;
        return version;
    }

    public String getUrl() {
        return url;
    }

    public String getClazz() {
        return interfaceName;
    }

    public Class getServiceClass() {
        return ReflectionUtils.loadClass(this.interfaceName);
    }

    public String getGroup() {
        return this.group;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCacheKey() {
        StringBuilder sb = new StringBuilder();
        if (useZk) {
            sb.append("zk=").append(zk).append("&");
        } else {
            sb.append("url=").append(url).append("&");
        }
        sb.append("interface=")
                .append(interfaceName)
                .append("&");
        if (StringUtils.isNotBlank(getVersion())) {
            sb.append("version=").append(getVersion()).append("&");
        }
        if (StringUtils.isNotBlank(group)) {
            sb.append("group=").append(group).append("&");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String zk="123:123,123:987,123:432";
        String[] strings=zk.split(",");
        List list= Lists.newArrayList(strings);
        Collections.sort(list);
        zk=StringUtils.join(list,",");
        System.out.println(zk);
    }
}
