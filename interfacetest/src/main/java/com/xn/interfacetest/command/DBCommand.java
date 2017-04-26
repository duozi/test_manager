package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/8/31.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.interfacetest.util.DBUtil;

public class DBCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DBCommand.class);
    private String sql;
    private String name;

    public DBCommand(String name,String sql) {
        this.name=name;
        this.sql = sql;
    }

    @Override
    public void execute() {
        if (sql.toLowerCase().startsWith("select")) {
            DBUtil.selectFromDB(name,sql);
        }else if(sql.toLowerCase().startsWith("update")){
            DBUtil.updateDB(name,sql);
        }
    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }
}
