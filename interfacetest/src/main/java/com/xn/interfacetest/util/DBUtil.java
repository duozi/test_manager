package com.xn.interfacetest.util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.xn.interfacetest.Enum.DatabaseTypeEnum;
import com.xn.interfacetest.service.impl.DBService;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.interfacetest.api.DataAssertService;
import com.xn.interfacetest.api.RelationCaseDatabaseService;
import com.xn.interfacetest.api.TestDatabaseConfigService;
import com.xn.interfacetest.dto.DataAssertDto;
import com.xn.interfacetest.dto.RelationCaseDatabaseDto;
import com.xn.interfacetest.dto.TestDatabaseConfigDto;

/**
 * Created by xn056839 on 2016/8/30.
 */

public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    public static HashMap<String, DBService> DbMap = new HashMap<String, DBService>();

    static TestDatabaseConfigService testDatabaseConfigService = (TestDatabaseConfigService) SpringContextUtil.getBean("testDatabaseConfigService");

    static RelationCaseDatabaseService relationCaseDatabaseService = (RelationCaseDatabaseService) SpringContextUtil.getBean("relationCaseDatabaseService");

    static DataAssertService dataAssertService = (DataAssertService) SpringContextUtil.getBean("dataAssertService");

    private static Connection con;
    private static BasicDataSource bds = null;

    public static boolean DBInit() {
//        GetPara getPara = new GetPara();
//        String path = getPara.getPath();
//        File file = new File(path + "suite/jdbc.properties");
//        String names = StringUtil.getConfig(file, "db_name", "");
//        String[] dbName = names.split(",");
//        for (String name : dbName) {
//            String url = StringUtil.getConfig(file, name + ".jdbc_url", "");
//            String user = StringUtil.getConfig(file, name + ".jdbc_username", "");
//            String pwd = StringUtil.getConfig(file, name + ".jdbc_password", "");
//            if (!url.equals("") && !user.equals("") && !pwd.equals("")) {
//                DBService dbService = new DBService(url, user, pwd);
//
//                try {
//                    dbService.newDB();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//
//                DbMap.put(name, dbService);
//                logger.info("new db ----{}", name);
//            }
//        }
        return true;
    }

    public static boolean getDBInit(Long environmentId,Long caseId){
        //得到数据清除或数据准备时db信息----
        List<RelationCaseDatabaseDto> relationCaseDatabaseList = relationCaseDatabaseService.getByCaseId(caseId);
        //得到数据校验时的db信息
        List<DataAssertDto> dataAssertDtoList = dataAssertService.getByCaseId(caseId);

        for(RelationCaseDatabaseDto databaseDto : relationCaseDatabaseList){
            //得到环境中数据库的地址信息和配置信息
            String dbName = databaseDto.getDatabaseName();

            //通过数据库配置名称拿到具体的数据库名称
            TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByName(dbName);
            if(null != testDatabaseConfigDto){
                dbName = testDatabaseConfigDto.getDatabaseName();
                if(!setDbMap( dbName, environmentId)){
                    return false;
                }
            }
        }
         for(DataAssertDto dataAssertDto : dataAssertDtoList){
             //得到环境中数据库的地址信息和配置信息
             String dbName = dataAssertDto.getDatabaseName();

             //通过数据库配置名称拿到具体的数据库名称
             TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByName(dbName);
             if(null != testDatabaseConfigDto) {
                 dbName = testDatabaseConfigDto.getDatabaseName();
                 if (!setDbMap(dbName, environmentId)) {
                     return false;
                 }
             }
         }

        return true;
    }

    /**
     * 创建数据库连接
     * @param dbName
     * @param environmentId
     * @return
     */
    private static boolean setDbMap (String dbName,Long environmentId){
        TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByEnvironmentAndDbName(dbName,environmentId);
        if(null != testDatabaseConfigDto){
            String url = testDatabaseConfigDto.getIpAddress();
            String user = testDatabaseConfigDto.getUserName();
            String pwd = testDatabaseConfigDto.getPassword();
            if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pwd)) {
                jdbc:mysql://10.17.2.114:3306/usernew?useUnicode=true&characterEncoding=utf-8
                url = "jdbc:" + DatabaseTypeEnum.getName(testDatabaseConfigDto.getType()) + "://" + url + ":" + testDatabaseConfigDto.getPortAddress()
                        + "/" + testDatabaseConfigDto.getDatabaseName() + "?useUnicode=true&characterEncoding=utf-8";
                DBService dbService = new DBService(url, user, pwd);
                try {
                    dbService.newDB();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }

                DbMap.put(dbName, dbService);
                logger.info("new db ----{}", dbName);
            }
        } else {
            return false;
        }
        return true;
    }

    public static void DBClose() {
        for (String name : DbMap.keySet()) {
            DBService dbService = DbMap.get(name);
            dbService.closeDB();
        }
    }

    public static String getCountFromDB(String name, String sql) {
        DBService dbService = DbMap.get(name);
        return dbService.getCountFromDB(sql);
    }

    public static ResultSet selectFromDB(String name, String sql) {
        DBService dbService = DbMap.get(name);
        return dbService.selectFromDB(sql);
    }

    public static int updateDB(String name, String sql) {
        DBService dbService = DbMap.get(name);
        return dbService.updateDB(sql);
    }

    public static void main(String[] args) {
//        selectFromDB("");
//        int i=updateDB("UPDATE customer_info set ENC_TYPE=\"KK\" WHERE MOBILE=\"18514762028\"");
//        System.out.println(i);
        String s = "12";
        String[] s1 = s.split(",");
        System.out.println(s1[0]);
    }
}
