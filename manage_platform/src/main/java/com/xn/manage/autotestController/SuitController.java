package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.RelationSuitCase;
import com.xn.interfacetest.entity.TestInterface;
import com.xn.interfacetest.entity.TestSuit;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.*;
import com.xn.performance.util.CommonResult;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.ModCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


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

		//id
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id) && !"null".equals(id)){
			TestSuitDto testSuitDto = testSuitService.get(Long.parseLong(id));
			map.put("testSuitDto", testSuitDto);
		}

		map.put("caseTypeEnums",CaseTypeEnum.values());
		map.put("interfaceTypeList", interfaceTypeEnumList);
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
}
