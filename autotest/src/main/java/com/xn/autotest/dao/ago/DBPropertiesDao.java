package com.xn.autotest.dao.ago;

import com.xn.autotest.bean.properties.DBProperties;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xn056839 on 2016/12/21.
 */
@Repository
public interface DBPropertiesDao {
    List<DBProperties> getAllDBProperties();

    DBProperties getDBByName(@Param("dbName") String dbName);

    void deleteDBProperties(@Param("dbName") String dbName);
}
