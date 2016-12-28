package com.xn.autotest.dao.ago;

import com.xn.autotest.bean.request.PlanBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xn056839 on 2016/12/13.
 */
public interface PlanDao {
    int checkPlanName(@Param("planName") String planName);

    List<PlanBean> getAllPlan();
}
