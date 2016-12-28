package com.xn.autotest.database;/**
 * Created by xn056839 on 2016/12/21.
 */

import com.xn.autotest.bean.properties.dbProperties.dto.DbPropertiesDto;
import com.xn.autotest.bean.properties.dbProperties.service.DbPropertiesService;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PooledDbSource implements DataSource,InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PooledDbSource.class);

    @Autowired
    DbPropertiesService dbPropertiesService;

    private final static Map<String, DataSource> DATASOURCEWRAPPERS = new HashMap<String, DataSource>();
    private List<DbPropertiesDto> dbPropertiesList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<DbPropertiesDto> dbPropertiesDtoList=dbPropertiesService.list();
        this.dbPropertiesList = dbPropertiesDtoList;

    }


    private DbPropertiesDto getDBPropertiesByName(String DBName) {
        if (dbPropertiesList.isEmpty()) {
            return null;
        } else {
            for (DbPropertiesDto properties : dbPropertiesList) {
                if (properties.getDbName().equals(DBName)) {
                    return properties;
                }

            }
            return null;
        }
    }

    private DataSource getDataSource(String DBName) {
        DataSource dataSource = DATASOURCEWRAPPERS.get(DBName);
        if (dataSource == null) {

            dataSource = createDataSource(DBName);
            if (dataSource != null) {
                DATASOURCEWRAPPERS.put(DBName, dataSource);
            }
        }
        return dataSource;
    }

    private DataSource createDataSource(String DBName) {
        DbPropertiesDto dbProperties = getDBPropertiesByName(DBName);
        if (dbProperties == null) {
            return null;

        }
        String driverClass = dbProperties.getDbDriver();
        String url = dbProperties.getUrl();
        String username = dbProperties.getUrl();
        String password = dbProperties.getPwd();


        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            logger.error("{} 数据库驱动不存在，请确保引入了对应的jar包依赖，或者确认你配置的驱动类名称是否正确", driverClass, e);
            throw new RuntimeException("数据库驱动不存在", e);
        }

        PoolConfiguration p = new PoolProperties();

        p.setValidationQuery("select 1");
        p.setTestOnBorrow(true);
        p.setTestWhileIdle(true);
        p.setFairQueue(false);
        p.setJmxEnabled(false);
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setCommitOnReturn(true);
        p.setMinIdle(1);
        p.setMaxIdle(1);
        p.setMaxActive(40);
        p.setInitialSize(1);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(10);
        p.setMinEvictableIdleTimeMillis(20000);
        p.setLogAbandoned(false);
        p.setRemoveAbandoned(false);
        p.setDriverClassName(driverClass);
        p.setUrl(url);
        p.setUsername(username);
        p.setPassword(password);
        org.apache.tomcat.jdbc.pool.DataSource datasource = null;
        datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);
        return datasource;
    }

    public void delDataSource(String dbName){
     DATASOURCEWRAPPERS.remove(dbName);
        DbPropertiesDto dbPropertiesDto=new DbPropertiesDto();
        dbPropertiesDto.setDbName(dbName);
        dbPropertiesService.delete(dbPropertiesDto);
    }
    public String getDataBaseType(String dbName) {
        DbPropertiesDto dbPropertiesDto=new DbPropertiesDto();
        dbPropertiesDto.setDbName(dbName);
        DbPropertiesDto dbPropertiesDtoResponse = dbPropertiesService.get(dbPropertiesDto);
        String jdbcUrl = dbPropertiesDtoResponse.getUrl();
        jdbcUrl = jdbcUrl.substring("jdbc:".length());
        return parseUrl(jdbcUrl);
    }

    private String parseUrl(String url) {
        URI uri = URI.create(url);
        return uri.getScheme();
    }


    public Connection getConnection(String dbName) throws SQLException {
        DataSource dataSource = getDataSource(dbName);
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public static void main(String[] args) {
        PooledDbSource pooledDbSource =new PooledDbSource();
    }


}
