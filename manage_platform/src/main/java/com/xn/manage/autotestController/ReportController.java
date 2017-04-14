package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.service.TestEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestPlanService;
import com.xn.interfacetest.service.TestSystemService;

@Controller
@RequestMapping("/autotest/report")
public class ReportController {
	@Autowired
	private TestSystemService systemService;

	@Autowired
	private TestPlanService testPlanService;

	@Autowired
	private TestEnvironmentService testEnvironmentService;


	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getReportPage(@PathVariable String  path, ModelMap model) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		//测试计划
		List<TestPlanDto> planList = new ArrayList<TestPlanDto>();
		TestPlanDto planDto = new TestPlanDto();
		planList = testPlanService.list(planDto);

		List<TestEnvironmentDto> testEnvironmentDtoList = new ArrayList<TestEnvironmentDto>();
		TestEnvironmentDto testEnvironmentDto = new TestEnvironmentDto();
		testEnvironmentDtoList = testEnvironmentService.list(testEnvironmentDto);

		model.put("testEnvironmentDtoList",testEnvironmentDtoList);
		model.put("planList",planList);
		model.put("systemList",systemList);
		return "/autotest/report/" + path;
	}

}
