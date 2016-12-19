package com.xn.autotest.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by xn056839 on 2016/12/13.
 */
public interface PlaneDao {
   int checkPlaneName(@Param("planeName") String planeName);
}
