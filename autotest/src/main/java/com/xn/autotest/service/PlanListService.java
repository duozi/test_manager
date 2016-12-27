package com.xn.autotest.service;

import com.xn.autotest.bean.request.plan.dto.PlanDto;

import java.util.List;

/**
 * Created by xn056839 on 2016/12/21.
 */

public interface PlanListService {
    public List<PlanDto> getPlanByParams();

    public boolean checkPlanName(String planName);
}
