package com.xn.performance.util.jmeter;/**
 * Created by xn056839 on 2017/4/18.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLite_Act {

    public static void sqlite3_from_influxdb(String dbname, String tabname,List<String> columns, List<List<Object>> value){
        Connection conn_sqlite = null;
        PreparedStatement preparedstatement = null;
        try{
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn_sqlite = DriverManager.getConnection("jdbc:sqlite:"+dbname);

            // 创建表
            preparedstatement = conn_sqlite.prepareStatement("drop table if exists " + tabname);
            preparedstatement.execute();
            StringBuffer sql_createtab = new StringBuffer("CREATE TABLE  ");
            sql_createtab.append(tabname);
            sql_createtab.append(" (id INTEGER PRIMARY KEY autoincrement");
            for (String col: columns){
                sql_createtab.append(","+ col + " CHAR(50)");
            }
            sql_createtab.append(")");
            System.out.println("sqlite3 create table sql is: "+sql_createtab.toString());
            preparedstatement = conn_sqlite.prepareStatement(sql_createtab.toString());
            preparedstatement.execute();
            // 插入表数据
            conn_sqlite.setAutoCommit(false);
            for(List<Object> row: value){
                StringBuffer sql_insertstr = new StringBuffer("insert into " + tabname + " (");
                for (int i=0;i<columns.size();i++){
                    if (i==0)
                        sql_insertstr.append(columns.get(i));
                    else
                        sql_insertstr.append(","+ columns.get(i));
                }
                sql_insertstr.append(") values(");
                for (int i=0; i<row.size();i++){
                    if (i==0)
                        sql_insertstr.append("?");
                    else
                        sql_insertstr.append(",?");
                }
                sql_insertstr.append(")");
                preparedstatement = conn_sqlite.prepareStatement(sql_insertstr.toString());
                for (int i=0; i<row.size();i++){
                    preparedstatement.setString(i+1,row.get(i).toString());
                }
                preparedstatement.execute();
            }
            conn_sqlite.commit();
        } catch (SQLException  e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }finally  {
            try {
                if (preparedstatement != null)
                    if (!preparedstatement.isClosed())
                        preparedstatement.close();
                if(conn_sqlite != null)
                    conn_sqlite.close();
            } catch (SQLException e){
                System.err.println(e);
            }
        }
    }


    public static void main( String args[] )
    {
        //sqlite3_act("D:/test.db");
    }
}

