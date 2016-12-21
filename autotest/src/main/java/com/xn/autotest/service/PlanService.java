package com.xn.autotest.service;/**
 * Created by xn056839 on 2016/12/13.
 */

import com.xn.autotest.dao.PlanDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
    private static final Logger logger = LoggerFactory.getLogger(PlanService.class);
    @Autowired
    PlanDao planDao;

    public boolean checkPlanName(String name) {
        int count = planDao.checkPlanName(name);
        if (count==0) {
            return true;
        } else {
            return false;
        }
    }
}
