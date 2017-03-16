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
import com.xn.interfacetest.entity.RelationCaseParams;
import com.xn.interfacetest.entity.TestParams;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.*;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);

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

	@Autowired
	TestParamsService testParamsService;

	@Autowired
	RelationCaseParamsService relationCaseParamsService;

	@RequestMapping(value="/case_item", method = RequestMethod.GET)
	public String getCaseItem(HttpServletRequest request, ModelMap map) {
		//用例类型
		String type=request.getParameter("caseType");
		map.put("caseType",type);

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
		return "/autotest/case/case_item";
	}

	@RequestMapping(value="/case_item_single", method = RequestMethod.GET)
	public String getCaseItemSingle(HttpServletRequest request, ModelMap map) {
		String caseId = request.getParameter("caseId");
		map.put("caseId",caseId);

		//参数类型
		map.put("paramTypeList",ParamTypeEnum.values());

		//单个用例
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id) && !"null".equals(id)){
			TestCaseDto testCaseDto = testCaseService.get(id);
			map.put("testCaseDto",testCaseDto);

			//包含该用例的测试集
			List<TestSuitDto> testSuitDtoList = testSuitService.getSuitByCaseId(Long.parseLong(id));
			map.put("testSuitDtoList",testSuitDtoList);

			if(null != testCaseDto.getInterfaceId()){
				//查询参数列表
				TestParamsDto testParamsDto = new TestParamsDto();
				testParamsDto.setInterfaceId(testCaseDto.getInterfaceId());
				List<TestParamsDto> testParamsDtoList = testParamsService.list(testParamsDto);
				map.put("testParamsDtoList",testParamsDtoList);
			}

			//查询已添加的参数值
			RelationCaseParamsDto relationCaseParamsDto = new RelationCaseParamsDto();
			relationCaseParamsDto.setCaseId(testCaseDto.getId());
			List<RelationCaseParamsDto> relationCaseParamsDtoList = relationCaseParamsService.list(relationCaseParamsDto);


		}

		map.put("redisAssertTypeEnumList",RedisAssertTypeEnum.values());
		map.put("redisOperationTypeEnumList",RedisOperationTypeEnum.values());
		map.put("appendParamList",AppendParamEnum.values());
		return "/autotest/case/case_item_single";
	}

	@RequestMapping(value="/case_item_multiple", method = RequestMethod.GET)
	public String getCaseItemMultiple(HttpServletRequest request, ModelMap map) {
		String caseId = request.getParameter("caseId");
		map.put("caseId",caseId);

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

			if(null != testCaseDto.getInterfaceId()){
				//查询参数列表
				TestParamsDto testParamsDto = new TestParamsDto();
				testParamsDto.setInterfaceId(testCaseDto.getInterfaceId());
				List<TestParamsDto> testParamsDtoList = testParamsService.list(testParamsDto);
				map.put("testParamsDtoList",testParamsDtoList);
			}

			//查询已添加的参数值
			RelationCaseParamsDto relationCaseParamsDto = new RelationCaseParamsDto();
			relationCaseParamsDto.setCaseId(testCaseDto.getId());
			List<RelationCaseParamsDto> relationCaseParamsDtoList = relationCaseParamsService.list(relationCaseParamsDto);


		}

		map.put("redisAssertTypeEnumList",RedisAssertTypeEnum.values());
		map.put("redisOperationTypeEnumList",RedisOperationTypeEnum.values());
		map.put("appendParamList",AppendParamEnum.values());
		return "/autotest/case/case_item_multiple";
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

	@RequestMapping(value="/saveCaseSimple", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveCaseSimple(TestCaseDto testCaseDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testCaseDto.getName()) || "null".equals(testCaseDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例名称");
				return result;
			}

			if(StringUtils.isBlank(testCaseDto.getNumber()) || "null".equals(testCaseDto.getNumber())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例编号");
				return result;
			}

			testCaseDto = testCaseService.save(testCaseDto);
			result.setData(testCaseDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveCustomParams", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveCustomParams(TestCaseDto testCaseDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testCaseDto.getName()) || "null".equals(testCaseDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例名称");
				return result;
			}

			if(StringUtils.isBlank(testCaseDto.getNumber()) || "null".equals(testCaseDto.getNumber())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例编号");
				return result;
			}

			testCaseDto = testCaseService.save(testCaseDto);
			result.setData(testCaseDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/testFormatParams", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult testFormatParams(TestCaseDto testCaseDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testCaseDto.getName()) || "null".equals(testCaseDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例名称");
				return result;
			}

			if(StringUtils.isBlank(testCaseDto.getNumber()) || "null".equals(testCaseDto.getNumber())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例编号");
				return result;
			}

			testCaseDto = testCaseService.save(testCaseDto);
			result.setData(testCaseDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteParams", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteParams(TestCaseDto testCaseDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testCaseDto.getName()) || "null".equals(testCaseDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例名称");
				return result;
			}

			if(StringUtils.isBlank(testCaseDto.getNumber()) || "null".equals(testCaseDto.getNumber())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写用例编号");
				return result;
			}

			testCaseDto = testCaseService.save(testCaseDto);
			result.setData(testCaseDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}
}
