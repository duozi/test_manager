package com.xn.manage.autotestController;

<<<<<<< HEAD
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.ContentTypeEnum;
import com.xn.manage.Enum.HttpTypeEnum;
import com.xn.manage.Enum.RequestTypeEnum;
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> hezhouxiyiyangde
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
import com.xn.interfacetest.api.RelationSuitCaseService;
import com.xn.interfacetest.api.TestInterfaceService;
import com.xn.interfacetest.api.TestServiceService;
import com.xn.interfacetest.api.TestSuitService;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.RelationSuitCaseDto;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.dto.TestSystemDto;


@Controller
@RequestMapping("/autotest/suit")
public class SuitController {
<<<<<<< HEAD
=======
	private static final Logger logger = LoggerFactory.getLogger(SuitController.class);

>>>>>>> hezhouxiyiyangde
	@Autowired
	private TestServiceService serviceService;

	@Autowired
	private TestSystemService systemService;
<<<<<<< HEAD
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSuitPage(@PathVariable String  path, ModelMap map) {
		List<ContentTypeEnum> contentTypeList = new ArrayList<ContentTypeEnum>();
		for(ContentTypeEnum item : ContentTypeEnum.values()){
			contentTypeList.add(item);
=======

	@Autowired
	private TestSuitService testSuitService;

	@Autowired
	private TestInterfaceService testInterfaceService;

	@Autowired
	private RelationSuitCaseService relationSuitCaseService;

	@RequestMapping(value="/suit_list", method = RequestMethod.GET)
	public String getSuitPage(HttpServletRequest request, ModelMap map) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		Map<String,Object> params = new HashMap<String,Object>();

		String systemId = request.getParameter("selectSystemId");
		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",systemId);
			map.put("systemId",systemId);
>>>>>>> hezhouxiyiyangde
		}

		String name = request.getParameter("name");
		if(StringUtils.isNotBlank(name) && !"null".equals(name)){
			params.put("name",name);
			map.put("name",name);
		}

		List<TestSuitDto> testSuitDtoList = testSuitService.listWithSystemAndInterface(params);

		map.put("testSuitDtoList",testSuitDtoList);
		map.put("systemList",systemList);
		return "/autotest/suit/suit_list";
	}

	@RequestMapping(value="/suit_item", method = RequestMethod.GET)
	public String getSuitItem(HttpServletRequest request, ModelMap map) {
		//查询接口信息
		List<TestInterfaceDto> testInterfaceDtoList = testInterfaceService.listAll();
		map.put("testInterfaceDtoList",testInterfaceDtoList);

		//接口类型--http，dubbo
		List<InterfaceTypeEnum> interfaceTypeEnumList=new ArrayList<InterfaceTypeEnum>();
		for(InterfaceTypeEnum item: InterfaceTypeEnum.values()){
			interfaceTypeEnumList.add(item);
		}

<<<<<<< HEAD
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);
=======
		//id
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id) && !"null".equals(id)){
			TestSuitDto testSuitDto = testSuitService.get(Long.parseLong(id));
			map.put("testSuitDto", testSuitDto);
		}

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);
>>>>>>> hezhouxiyiyangde

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
			String interfaceIdStr = request.getParameter("interfaceId");
			Long interfaceId = null;
			if (StringUtils.isNotBlank(interfaceIdStr) && !"null".equals(interfaceIdStr)) {
				interfaceId = Long.parseLong(interfaceIdStr);
			}

			String suitIdStr = request.getParameter("suitId");
			Long suitId = null;
			if (StringUtils.isNotBlank(suitIdStr) && !"null".equals(suitIdStr)) {
				suitId = Long.parseLong(suitIdStr);
			}
//
			relationSuitCaseService.saveRelation(interfaceId,suitId,request.getParameter("caseIds"));
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
