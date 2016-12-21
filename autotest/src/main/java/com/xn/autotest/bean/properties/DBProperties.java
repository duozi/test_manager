package com.xn.autotest.bean.properties;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBProperties {
    private static final Logger logger = LoggerFactory.getLogger(DBProperties.class);
    /**
     * 数据库名不能重复
     */
    private String dbName;
    private String url;

    private  String userName;
    private String pwd;
    private String dbDriver;

    public DBProperties() {
    }

    public DBProperties(String dbName, String url, String userName, String pwd, String dbDriver) {
        this.dbName = dbName;
        this.url = url;

        this.userName = userName;
        this.pwd = pwd;
        this.dbDriver = dbDriver;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String DBType) {
        this.dbDriver = DBType;
    }
}
