package com.xn.autotest.service.serviceImpl;/**
 * Created by xn056839 on 2016/12/13.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.plan.service.PlanService;
import com.xn.autotest.service.PlanListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PlanListServiceImpl implements PlanListService {
    private static final Logger logger = LoggerFactory.getLogger(PlanListServiceImpl.class);
    @Autowired
    PlanService planService;


    @Override
    public List<PlanDto> getPlanByParams(Map<String, Object> map) {
        Integer planId = (Integer) map.get("planId");
        String systemName = (String) map.get("systemName");
        Integer executeType = (Integer) map.get("executeType");
        Integer planType = (Integer) map.get("planType");

        PlanDto planDto = new PlanDto();
        planDto.setId(planId);
        planDto.setExecuteType(executeType);
        planDto.setPlanType(planType);
        List<PlanDto> planDtoList = planService.list(planDto);
        List<PlanDto> returnList = Lists.newArrayList();
        for (PlanDto plan : planDtoList) {

            Set<String> set = Sets.newHashSet(plan.getSystemName().split(","));

            if (set.contains(systemName)) {
                returnList.add(plan);

            }
        }

        return returnList;
    }

    @Override
    public List<PlanDto> getSystemByParams() {
        return null;
    }
}
