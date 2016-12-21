package com.xn.autotest.bean.assertBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBAssert {
    private static final Logger logger = LoggerFactory.getLogger(DBAssert.class);
    private String DBName;
    private String sql;
    /**
     * 数据库校验内容，用json表示
     */
    private String DBAssertContent;

    public DBAssert(String DBName, String sql, String DBAssertContent) {
        this.DBName = DBName;
        this.sql = sql;
        this.DBAssertContent = DBAssertContent;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDBAssertContent() {
        return DBAssertContent;
    }

    public void setDBAssertContent(String DBAssertContent) {
        this.DBAssertContent = DBAssertContent;
    }
}
