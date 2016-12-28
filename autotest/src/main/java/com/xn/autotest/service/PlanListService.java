package com.xn.autotest.service;

import com.xn.autotest.bean.CommonResult;
import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.system.dto.SystemDto;

import java.util.List;
import java.util.Map;

/**
 * Created by xn056839 on 2016/12/21.
 */

public interface PlanListService {
    CommonResult<List<PlanDto>> getPlanByParams(Map<String, Object> map);

    CommonResult<SystemDto> getSystemByParam(SystemDto systemDto);

    CommonResult<PlanDto> getPlanById(PlanDto planDto);

    CommonResult<PlanDto> savePlan(PlanDto planDto);

    CommonResult<Integer> updatePlanById(PlanDto planDto);

    CommonResult<Integer> deletePlanById(PlanDto planDto);



}
