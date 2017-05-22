package com.xn.performance.util.jmeter;/**
 * Created by xn056839 on 2017/4/18.
 */

import java.sql.*;
import java.util.ArrayList;
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

    /**
     * 查询对应 sqlite3 db 包含的所有表名称
     * @param dbname 数据库路径+名称，例如 target/jmeter-reports/10min-go.db
     * @return List<String>类型表名list
     */
    public static List<String> gettablename(String dbname){
        List<String> tablename = new ArrayList<String>();
        Connection conn_sqlite = null;
        PreparedStatement preparedstatement = null;
        try{
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn_sqlite = DriverManager.getConnection("jdbc:sqlite:"+dbname);

            // 查询数据库有什么表
            preparedstatement = conn_sqlite.prepareStatement("select name from sqlite_master where type='table' order by name");//".table"
            //preparedstatement.execute();
            ResultSet rs = preparedstatement.executeQuery();

            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount(); // 获取列数
            //System.out.println(rsmd.getColumnName(1)); //获取列名从 1 开始
            //System.out.println(rsmd.getColumnName(2));

			/* ResultSet 没有直接获取行数的api，用游标直接移动到最后一行，获取对应行号，从1开始
			SQLITE不支持游标。会报 java.sql.SQLException: ResultSet is TYPE_FORWARD_ONLY 异常
			rs.last();
			int rowCount= rs.getRow(); // 获取当前行号
			*/

            int rowCount=0; // 计算行数
            while(rs.next()){
                rowCount = rowCount + 1;
                //System.out.println("rs.getString(1) is " + rs.getString(1));
                tablename.add(rs.getString(1));
                System.out.println("the table name is " + rs.getString(1));
            }
            System.out.println(String.format("查询结果列数 %d, 行数 %d",columnCount,rowCount));
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
        return tablename;
    }

    /**
     * 查询数据库数据，返回 List<List<String>> 对象
     * @param dbname   数据库路径+名称，例如 target/jmeter-reports/10min-go.db
     * @param sqlstring  查询 sql, 参数用 ? 表示例如 select * from tb_name where col1= ? and col2 = ?
     * @param params 对应上面sql的参数
     * @return  List<List<String>> 对象, 第一行（下标0）是标题行（db table 列名），从第二行（下标1）是表的值
     */
    public static List<List<String>> selectData(String dbname, String sqlstring, String... params){
        ResultSet rs=null;
        List<List<String>> tabvalue = new ArrayList<List<String>>();
        Connection conn_sqlite = null;
        PreparedStatement preparedstatement = null;
        try{
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn_sqlite = DriverManager.getConnection("jdbc:sqlite:"+dbname);

            // 查询数据库有什么表
            preparedstatement = conn_sqlite.prepareStatement(sqlstring);
            for(int i=0;i<params.length;i++){
                preparedstatement.setString(i+1,params[i]); //prepareStatement 参数计数从1开始
            }
            //preparedstatement.execute();
            rs = preparedstatement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount(); // 获取列数
            List<String> tableth=new ArrayList<String>();
            for (int i=1;i<=columnCount;i++){
                tableth.add(rsmd.getColumnName(i));
            }
            tabvalue.add(tableth); //将表标题添加到 List<List<String>> 对象
            // 将表数据添加到 List<List<String>> 对象
            while(rs.next()){
                //System.out.println("the table value is " + rs.getString(1));
                List<String> tabletr=new ArrayList<String>();
                for (int i=1;i<=columnCount;i++){
                    tabletr.add(rs.getString(i));
                }
                tabvalue.add(tabletr); //将表一行数据添加到 List<List<String>> 对象
            }
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
        return tabvalue;
    }

}

