package com.xn.autotest.controller;/**
 * Created by xn056839 on 2016/12/21.
 */

import com.alibaba.fastjson.JSONObject;
import com.xn.autotest.database.PooledDbSource;
import com.xn.autotest.service.serviceImpl.PlanListServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/new_plane")
public class NewPlanController {
    private static final Logger logger = LoggerFactory.getLogger(NewPlanController.class);
    @Resource
    PlanListServiceImpl planListService;
    @Resource
    PooledDbSource pooledDbSource;

//    @RequestMapping(value = "check_plane_name", method = RequestMethod.GET)
//    @ResponseBody
//    public String checkPlaneName(@RequestParam(value = "planName") String planName) {
//        logger.info("request parameterAssert is :{}", planName);
//        JSONObject result = new JSONObject();
//        boolean planeNameUsable = planServiceImpl.checkPlanName(planName);
//        result.put("usable", planeNameUsable);
//        return result.toString();
//
//    }
    @RequestMapping(value = "check", method = RequestMethod.GET)
    @ResponseBody
    public String checkDbName(@RequestParam(value = "planName") String planName) {
        logger.info("request parameterAssert is :{}", planName);
       String s=pooledDbSource.getDataBaseType("abc");
        JSONObject result = new JSONObject();

        result.put("usable", s);
        return result.toString();

    }
}
