package com.xn.interfacetest.util;/**
 * Created by xn056839 on 2016/11/18.
 */


import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool{
    private static BasicDataSource bds = null;
    public static DataSource setupDataSource(){
        bds = new BasicDataSource();
        //设置驱动程序
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        //设置连接用户名
        bds.setUsername("root");
        //设置连接密码
        bds.setPassword("root");
        //设置连接地址
        bds.setUrl("jdbc:mysql://localhost:3306/databasename");
        //设置初始化连接总数
        bds.setInitialSize(50);
        //设置同时应用的连接总数
        bds.setMaxActive(-1);
        //设置在缓冲池的最大连接数
        bds.setMaxIdle(-1);
        //设置在缓冲池的最小连接数
        bds.setMinIdle(0);
        //设置最长的等待时间
        bds.setMaxWait(-1);

        return (DataSource)bds;
    }
    //显示连接池的连接个数的方法
    public static void printDataSourceStats(DataSource ds) throws SQLException{
        bds = (BasicDataSource)ds;
        System.out.println();
        System.out.println();
    }
    //关闭连接池的方法
    public static void shutdownDataSource(DataSource ds) throws SQLException{
        bds = (BasicDataSource)ds;
        bds.close();
    }
    public void use(String sql){
        DataSource ds=setupDataSource();
        try {
            Connection connect= ds.getConnection();
            Statement st = connect.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
