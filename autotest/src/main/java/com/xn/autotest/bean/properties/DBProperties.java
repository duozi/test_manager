package com.xn.autotest.bean.properties;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBProperties {
    private static final Logger logger = LoggerFactory.getLogger(DBProperties.class);
    private String DBName;
    private String url;
    private  String databaseName;
    private  String userName;
    private String pwd;
    private String DBType;

    public DBProperties(String DBName, String url, String databaseName, String userName, String pwd, String DBType) {
        this.DBName = DBName;
        this.url = url;
        this.databaseName = databaseName;
        this.userName = userName;
        this.pwd = pwd;
        this.DBType = DBType;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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

    public String getDBType() {
        return DBType;
    }

    public void setDBType(String DBType) {
        this.DBType = DBType;
    }
}
