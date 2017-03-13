package com.xn.manage.autotestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.*;
import com.xn.performance.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/autotest/case")
public class CaseController {

	@Resource
	TestSystemService testSystemService;

	@Resource
	TestServiceService testServiceService;

	@Resource
	CompanyService companyService;

	@Autowired
	TestCaseService testCaseService;

	@Autowired
	TestInterfaceService testInterfaceService;

	@Autowired
	TestSuitService testSuitService;

	@RequestMapping(value="/case_item", method = RequestMethod.GET)
	public String getCaseItem(HttpServletRequest request, ModelMap map) {
		//用例类型
		String type=request.getParameter("caseType");
		map.put("caseType",type);

		//参数类型
		map.put("paramTypeList",ParamTypeEnum.values());

		//接口列表
		List<TestInterfaceDto> interfaceDtoList = testInterfaceService.list(new TestInterfaceDto());
		map.put("interfaceList",interfaceDtoList);

		//单个用例
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id) && !"null".equals(id)){
			TestCaseDto testCaseDto = testCaseService.get(id);
			map.put("testCaseDto",testCaseDto);

			//包含该用例的测试集
			List<TestSuitDto> testSuitDtoList = testSuitService.getSuitByCaseId(Long.parseLong(id));
			map.put("testSuitDtoList",testSuitDtoList);
		}





		map.put("redisAssertTypeEnumList",RedisAssertTypeEnum.values());
		map.put("redisOperationTypeEnumList",RedisOperationTypeEnum.values());
		map.put("appendParamList",AppendParamEnum.values());
		return "/autotest/case/case_item";
	}

	@RequestMapping(value="/case_list", method = RequestMethod.GET)
	public String getCasePage(HttpServletRequest request, ModelMap map) {
		return "/autotest/case/case_list";
	}

	@RequestMapping(value = "/getCaseByInterfaceId")
	@ResponseBody
	public List<TestCaseDto> getCaseByInterfaceId(HttpServletRequest request) {
		List<TestCaseDto> testCaseDtoList = new ArrayList<TestCaseDto>();
		Map<String,Object> params = new HashMap<String, Object>();

		String interfaceId = request.getParameter("interfaceId");
		if(StringUtils.isNotBlank(interfaceId) && !"null".equals(interfaceId)){
			params.put("interfaceId",interfaceId);
		}

		String caseType = request.getParameter("caseType");
		if(StringUtils.isNotBlank(caseType) && !"null".equals(caseType)){
			params.put("type",caseType);
		}

		//查询指定接口的指定类型的用例
		testCaseDtoList = testCaseService.list(params);
		return testCaseDtoList;
	}



}
