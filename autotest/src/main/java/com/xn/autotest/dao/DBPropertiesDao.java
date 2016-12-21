package com.xn.autotest.dao;

import com.xn.autotest.bean.properties.DBProperties;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xn056839 on 2016/12/21.
 */
public interface DBPropertiesDao {
    List<DBProperties> getAllDBProperties();

    DBProperties getDBByName(@Param("dbName") String dbName);
}
