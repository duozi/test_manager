package com.xn.autotest.service;

import com.xn.autotest.bean.request.plan.dto.PlanDto;

import java.util.List;
import java.util.Map;

/**
 * Created by xn056839 on 2016/12/21.
 */

public interface PlanListService {
    public List<PlanDto> getPlanByParams(Map<String ,Object> map);
    public List<PlanDto> getSystemByParams();

}
