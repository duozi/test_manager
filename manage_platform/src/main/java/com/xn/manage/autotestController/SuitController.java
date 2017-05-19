package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.*;
import com.xn.interfacetest.dto.*;
import com.xn.manage.utils.ModelUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.common.base.CommonResult;
import com.xn.interfacetest.Enum.CaseTypeEnum;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.Enum.InterfaceTypeEnum;


@Controller
@RequestMapping("/autotest/suit")
public class SuitController {
	private static final Logger logger = LoggerFactory.getLogger(SuitController.class);

	@Autowired
	private TestServiceService serviceService;

	@Autowired
	private TestSystemService systemService;

	@Autowired
	private TestSuitService testSuitService;

	@Autowired
	private TestCaseService testCaseService;

	@Autowired
	private TestInterfaceService testInterfaceService;

	@Autowired
	private RelationSuitCaseService relationSuitCaseService;

	@RequestMapping(value="/suit_list", method = RequestMethod.GET)
	public String getSuitPage(HttpServletRequest request, ModelMap map, PageInfo pageInfo) {
		StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		Map<String,Object> params = new HashMap<String,Object>();

		String systemId = request.getParameter("selectSystemId");
		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",systemId);
			map.put("systemId",systemId);
			pageParams.append("&selectSystemId=").append(systemId);
		}

		String name = request.getParameter("name");
		if(StringUtils.isNotBlank(name) && !"null".equals(name)){
			params.put("name",name);
			map.put("name",name);
			pageParams.append("&name=").append(name);
		}


		if (pageInfo.getCurrentPage() < 1) {
			pageInfo.setCurrentPage(1);
		}
		pageInfo.setPagination(true);
		pageInfo.setPageSize(10);
		params.put("page", pageInfo);
		pageInfo.setParams(pageParams.toString());


		PageResult<TestSuitDto> testSuitDtoList = testSuitService.page(params);
		ModelUtils.setResult(map, testSuitDtoList.getPage(), testSuitDtoList.getList());

		map.put("systemList",systemList);
		return "/autotest/suit/suit_list";
	}

	@RequestMapping(value="/suit_item", method = RequestMethod.GET)
	public String getSuitItem(HttpServletRequest request, ModelMap map) {
//		//查询接口信息
//		List<TestInterfaceDto> testInterfaceDtoList = testInterfaceService.listAll();
//		map.put("testInterfaceDtoList",testInterfaceDtoList);


		//接口类型--http，dubbo
		List<InterfaceTypeEnum> interfaceTypeEnumList=new ArrayList<InterfaceTypeEnum>();
		for(InterfaceTypeEnum item: InterfaceTypeEnum.values()){
			interfaceTypeEnumList.add(item);
		}

		//id
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id) && !"null".equals(id)){
			TestSuitDto testSuitDto = testSuitService.get(Long.parseLong(id));
			map.put("testSuitDto", testSuitDto);

			//查询用例信息，以接口id排序
			List<TestCaseDto> testCaseDtoList = testCaseService.listBySuitIdOrderByInterfaceId(Long.parseLong(id));
			map.put("testCaseDtoList", testCaseDtoList);
		}

		//查询所有接口的用例,以接口排序
		//查询用例信息，以接口id排序
		List<TestCaseDto>  caseList = testCaseService.listAllOrderByInterface();
		map.put("caseList", caseList);

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		map.put("caseTypeEnums",CaseTypeEnum.values());
		map.put("interfaceTypeList", interfaceTypeEnumList);
		map.put("systemList",systemList);
		return "/autotest/suit/suit_item";
	}

	@RequestMapping(value="/deleteSuit", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteSuit(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testSuitService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message = e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除操作异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 保存测试集基础信息
	 * @param testSuitDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveSuit", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveSuit(TestSuitDto testSuitDto,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			testSuitDto = testSuitService.save(testSuitDto);
			result.setData(testSuitDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存测试集异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 保存测试集和用例的关系
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveSuitCaseRelation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveSuitCaseRelation(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String suitIdStr = request.getParameter("suitId");
			Long suitId = null;
			if (StringUtils.isNotBlank(suitIdStr) && !"null".equals(suitIdStr)) {
				suitId = Long.parseLong(suitIdStr);
			}

			relationSuitCaseService.saveRelation(suitId,request.getParameter("caseIds"));
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存测试集异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value = "/getSuitCaseList")
	@ResponseBody
	public List<RelationSuitCaseDto> getSuitCaseList(HttpServletRequest request) {
		List<RelationSuitCaseDto> relationSuitCaseDtoList = new ArrayList<RelationSuitCaseDto>();
		Map<String,Object> params = new HashMap<String, Object>();

		String interfaceId = request.getParameter("interfaceId");
		if(StringUtils.isNotBlank(interfaceId) && !"null".equals(interfaceId)){
			params.put("interfaceId",interfaceId);
		}

		String suitId = request.getParameter("suitId");
		if(StringUtils.isNotBlank(suitId) && !"null".equals(suitId)){
			params.put("suitId",suitId);
		}

		//查询指定测试集的指定接口的用例
		relationSuitCaseDtoList = relationSuitCaseService.list(params);
		return relationSuitCaseDtoList;
	}

	/**
	 * 得到所有的测试集
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSuitList")
	@ResponseBody
	public List<TestSuitDto> getSuitList(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String, Object>();
		//查询指定测试集的指定接口的用例
		return testSuitService.listWithSystemAndInterface(params);
	}

	/**
	 * 得到指定测试计划的测试集
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPlanSuitList")
	@ResponseBody
	public List<TestSuitDto> getPlanSuitList(HttpServletRequest request) {
		//查询指定测试集的指定接口的用例
		List<TestSuitDto> list = new ArrayList<TestSuitDto>();
		String planId = request.getParameter("planId");
		if(StringUtils.isNotBlank(planId) && !"null".equals(planId)){
			list = testSuitService.getByPlanId(Long.parseLong(planId));
		}
		return list;
	}
}
