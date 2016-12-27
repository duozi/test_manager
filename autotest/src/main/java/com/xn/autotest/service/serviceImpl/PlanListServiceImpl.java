package com.xn.autotest.service.serviceImpl;/**
 * Created by xn056839 on 2016/12/13.
 */

import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.plan.service.impl.PlanServiceImpl;
import com.xn.autotest.service.PlanListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlanListServiceImpl implements PlanListService {
    private static final Logger logger = LoggerFactory.getLogger(PlanListServiceImpl.class);
    @Resource
    PlanServiceImpl planService;

    @Override
    public List<PlanDto> getAllPlan() {

        return planService.list();
    }

    @Override
    public boolean checkPlanName(String name) {
//        int count = planDao.checkPlanName(name);
//        if (count == 0) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }
}
