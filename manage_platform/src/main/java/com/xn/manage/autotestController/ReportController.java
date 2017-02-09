package com.xn.manage.autotestController;

import com.xn.manage.bean.System;
import com.xn.manage.entity.Plan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/report")
public class ReportController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getReportPage(@PathVariable String  path, ModelMap model) {
		List<System> systemList = new ArrayList<System>();
		systemList.add(new System(1,"风控规则"));
		systemList.add(new System(2,"支付中心"));
		systemList.add(new System(3,"征信公司"));
		systemList.add(new System(4,"商户平台"));

		//测试计划
		List<Plan> planList = new ArrayList<Plan>();
		planList.add(new Plan(1,"牛贷系统查询联系人测试计划"));

		model.put("planList",planList);
		model.put("systemList",systemList);
		return "/autotest/report/" + path;
	}

}
