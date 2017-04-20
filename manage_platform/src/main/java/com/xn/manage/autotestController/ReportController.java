package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xn.interfacetest.api.RelationInterfaceResultService;
import com.xn.interfacetest.api.TestCaseService;
import com.xn.interfacetest.api.TestEnvironmentService;
import com.xn.interfacetest.api.TestInterfaceService;
import com.xn.interfacetest.api.TestPlanService;
import com.xn.interfacetest.api.TestReportService;
import com.xn.interfacetest.api.TestSuitService;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.RelationInterfaceResultDto;
import com.xn.interfacetest.dto.TestCaseDto;
import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestReportDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.dto.TestSystemDto;

@Controller
@RequestMapping("/autotest/report")
public class ReportController {
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private TestSystemService systemService;

	@Autowired
	private TestPlanService testPlanService;

	@Autowired
	private TestEnvironmentService testEnvironmentService;

	@Autowired
	private TestReportService testReportService;

	@Autowired
	private RelationInterfaceResultService relationInterfaceResultService;

	@Autowired
	private TestInterfaceService testInterfaceService;

	@Autowired
	private TestSuitService testSuitService;

	@Autowired
	private TestCaseService testCaseService;

	@RequestMapping(value="/report_list", method = RequestMethod.GET)
	public String getReportPage(HttpServletRequest request, ModelMap model) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		//测试计划
		List<TestPlanDto> planList = new ArrayList<TestPlanDto>();
		TestPlanDto planDto = new TestPlanDto();
		planList = testPlanService.list(planDto);

		//测试环境
		List<TestEnvironmentDto> testEnvironmentDtoList = new ArrayList<TestEnvironmentDto>();
		TestEnvironmentDto testEnvironmentDto = new TestEnvironmentDto();
		testEnvironmentDtoList = testEnvironmentService.list(testEnvironmentDto);

		//查询出所有的测试报告
		Map<String,Object> params = new HashMap<String,Object>();
		String environmentId = request.getParameter("environmentId");
		if(StringUtils.isNotBlank(environmentId) && !"null".equals(environmentId)){
			params.put("environmentId",environmentId);
			model.put("environmentId",environmentId);
		}
		String planId = request.getParameter("planId");
		if(StringUtils.isNotBlank(planId) && !"null".equals(planId)){
			params.put("planId",planId);
			model.put("planId",planId);
		}
		String name = request.getParameter("name");
		if(StringUtils.isNotBlank(name) && !"null".equals(name)){
			params.put("name",name);
			model.put("name",name);
		}
		String beginTime = request.getParameter("beginTime");
		if(StringUtils.isNotBlank(beginTime) && !"null".equals(beginTime)){
			params.put("beginTime",beginTime);
			model.put("beginTime",beginTime);
		}
		String toTime = request.getParameter("endTime");
		if(StringUtils.isNotBlank(toTime) && !"null".equals(toTime)){
			params.put("toTime",toTime);
			model.put("toTime",toTime);
		}
		String result = request.getParameter("result");
		if(StringUtils.isNotBlank(result) && !"null".equals(result)){
			params.put("result",result);
			model.put("result",result);
		}

		List<TestReportDto> testReportDtoList = testReportService.selectWithOtherInfo(params);

		model.put("testReportDtoList",testReportDtoList);
		model.put("testEnvironmentDtoList",testEnvironmentDtoList);
		model.put("planList",planList);
		model.put("systemList",systemList);
		return "/autotest/report/report_list";
	}

	@RequestMapping(value="/report_detail", method = RequestMethod.GET)
	public String getReportDetail(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		model.put("id",id);
		if(StringUtils.isNotBlank(id)) {
			//执行信息--含环境信息、计划信息、测试集信息
			TestReportDto testReportDto = testReportService.getWithInfo(Long.parseLong(id));
			model.put("testReportDto",testReportDto);

			if(null != testReportDto){
				//查询公共信息---公司、部门、系统
				String systemIds = testReportDto.getSystemIds();
				List<TestSystemDto> testSystemDtoList = systemService.getWithCompanyInfoBySystems(systemIds);
				model.put("testSystemDtoList",testSystemDtoList);

				//查询被测接口详情信息
				String interfaceIds = testReportDto.getInterfaceIds();
				if(StringUtils.isNotBlank(interfaceIds)){
					String[] interfaceArray = interfaceIds.split(",|，");
					List<TestInterfaceDto> interfaceDtoList = testInterfaceService.listWithInfoByIds(interfaceArray);
					model.put("interfaceDtoList",interfaceDtoList);
				}
			}
		}

		return "/autotest/report/report_detail";
	}

	@RequestMapping(value="/interface_case_result", method = RequestMethod.GET)
	public String getInterfaceCaseResultDetail(HttpServletRequest request, ModelMap model) {
		Map<String,Object> params = new HashMap<String,Object>();

		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)) {
			params.put("reportId",id);
			model.put("id",id);
			TestReportDto testReportDto = testReportService.getWithInfo(Long.parseLong(id));
			if(null != testReportDto){
				if(null != testReportDto.getPlanId()){
					List<TestSuitDto> testSuitDtoList = testSuitService.getByPlanId(testReportDto.getPlanId());
					model.put("testSuitDtoList",testSuitDtoList);
				}
				 if(StringUtils.isNotBlank(testReportDto.getCaseIds())){
					 List<TestCaseDto> testCaseDtoList = testCaseService.getByCaseIds(testReportDto.getCaseIds());
					 model.put("testCaseDtoList",testCaseDtoList);
				 }
				if(StringUtils.isNotBlank(testReportDto.getInterfaceIds())){
					List<TestInterfaceDto> interfaceDtoList  = testInterfaceService.getByInterfaceIds(testReportDto.getInterfaceIds());
					model.put("interfaceDtoList",interfaceDtoList);
				}
			}
		}

		String suitId = request.getParameter("suitId");
		if(StringUtils.isNotBlank(suitId) && !"null".equals(suitId)) {
			params.put("suitId",suitId);
			model.put("suitId",suitId);
		}

		String caseId = request.getParameter("caseId");
		if(StringUtils.isNotBlank(caseId) && !"null".equals(caseId)) {
			params.put("caseId",caseId);
			model.put("caseId",caseId);
		}

		String result = request.getParameter("result");
		if(StringUtils.isNotBlank(result) && !"null".equals(result)) {
			params.put("result",result);
			model.put("result",result);
		}

		//查询每个接口的返回内容和校验内容
		List<RelationInterfaceResultDto> relationInterfaceResultDtoList = relationInterfaceResultService.getByReportId(params);

		model.put("relationInterfaceResultDtoList",relationInterfaceResultDtoList);
		return "autotest/report/interface_case_result";
	}


}
