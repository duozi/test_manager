package com.xn.autotest.bean.operation;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBOperation {
    private static final Logger logger = LoggerFactory.getLogger(DBOperation.class);
    private String DBName;
    /**
     * 数据库操作类型，1 beforeclass 2 afterclass 3 before ,4 after
     */
    private int type;
    /**
     * 执行顺序 从1开始
     */
    private  int operationOrder;
    private String sql;


}
