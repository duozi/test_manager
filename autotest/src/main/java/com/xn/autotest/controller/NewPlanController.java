package com.xn.autotest.controller;/**
 * Created by xn056839 on 2016/12/21.
 */

import com.alibaba.fastjson.JSONObject;
import com.xn.autotest.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/new_plane")
public class NewPlanController {
    private static final Logger logger = LoggerFactory.getLogger(NewPlanController.class);
    @Autowired
    PlanService planService;

    @RequestMapping(value = "check_plane_name", method = RequestMethod.GET)
    @ResponseBody
    public String checkPlaneName( @RequestParam(value = "planName", defaultValue = "World") String planName) {
        logger.info("request parameter is :{}", planName);
        JSONObject result = new JSONObject();
        boolean planeNameUsable=planService.checkPlanName(planName);
        result.put("usable",planeNameUsable);
        return result.toString();

    }

}
