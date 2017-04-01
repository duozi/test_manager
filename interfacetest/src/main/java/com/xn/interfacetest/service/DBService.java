package com.xn.interfacetest.service;/**
 * Created by xn056839 on 2016/11/29.
 */

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);
    private  Connection con;
    private  BasicDataSource bds = null;
    private String url;
    private String user;
    private String pwd;

    public DBService( String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    public  void newDB() throws SQLException {
//        GetPara getPara = new GetPara();
//        String path = getPara.getPath();
//        File file = new File(path + "suite/jdbc.properties");
//        String url = StringUtil.getConfig(file, "jdbc_url", "");
//        String user = StringUtil.getConfig(file, "jdbc_username", "");
//        String pwd = StringUtil.getConfig(file, "jdbc_password", "");

        bds = new BasicDataSource();
        //设置驱动程序
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        //设置连接用户名
        bds.setUsername(user);
        //设置连接密码
        bds.setPassword(pwd);
        //设置连接地址
        bds.setUrl(url);
        //设置初始化连接总数
        bds.setInitialSize(20);
        //设置同时应用的连接总数
        bds.setMaxActive(5);
        //设置在缓冲池的最大连接数
        bds.setMaxIdle(2);
        //设置在缓冲池的最小连接数
        bds.setMinIdle(0);
        //设置最长的等待时间
        bds.setMaxWait(5);
        try {
            con = bds.getConnection();


            logger.info("new DB connection");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e ;
        }


    }

    public  void closeDB() {
        try {
//            rs.close();
            //关闭语句
//            stmt.close();
            //关闭连接
            bds.close();
            logger.info("close DB connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  ResultSet selectFromDB(String sql) {
        ResultSet rs=null;
        try {
            Statement stmt = con.createStatement();
            logger.info("execute sql:{}", sql);
            rs= stmt.executeQuery(sql);

            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); ++i) {
//                    System.out.println(i + 1);
//                    System.out.println(rs.getString(i + 1));
                }
//                System.out.println();
            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return rs;
        }


    }

    public  String getCountFromDB(String sql) {
        String count = "-1";
        try {
            Statement stmt = con.createStatement();
            logger.info("execute sql:{}", sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                count = rs.getString(1);
                logger.info(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return count;
        }
    }

    public  int updateDB(String sql) {
        int rs = -1;
        try {
            Statement stmt = con.createStatement();
            logger.info("execute sql:{}", sql);
            rs = stmt.executeUpdate(sql);
//            System.out.println(rs.getString(1));
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return rs;
        }


    }
}
