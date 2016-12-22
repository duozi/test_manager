package com.xn.autotest.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by xn056839 on 2016/12/13.
 */
public interface PlanDao {
   int checkPlanName(@Param("planName") String planName);
}
