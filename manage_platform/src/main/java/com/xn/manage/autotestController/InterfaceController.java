package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.service.TestInterfaceService;
import com.xn.manage.Enum.*;
import com.xn.performance.util.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/autotest/interface")
public class InterfaceController {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);
	@Autowired
	private TestSystemService systemService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TestServiceService serviceService;

	@Autowired
	private TestInterfaceService testInterfaceService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getPlanPage(@PathVariable String  path , ModelMap map) {
		List<ContentTypeEnum> contentTypeList = new ArrayList<ContentTypeEnum>();
		for(ContentTypeEnum item : ContentTypeEnum.values()){
			contentTypeList.add(item);
		}

		List<HttpTypeEnum> httpTypeList = new ArrayList<HttpTypeEnum>();
		for(HttpTypeEnum item : HttpTypeEnum.values()){
			httpTypeList.add(item);
		}

		List<RequestTypeEnum> requestTypeList = new ArrayList<RequestTypeEnum>();
		for(RequestTypeEnum item : RequestTypeEnum.values()){
			requestTypeList.add(item);
		}
		List<RedisOperationTypeEnum> redisOperationTypeEnumList=new ArrayList<RedisOperationTypeEnum>();
		for(RedisOperationTypeEnum item:RedisOperationTypeEnum.values()){
			redisOperationTypeEnumList.add(item);
		}

		List<InterfaceTypeEnum> interfaceTypeEnumList=new ArrayList<InterfaceTypeEnum>();
		for(InterfaceTypeEnum item: InterfaceTypeEnum.values()){
			interfaceTypeEnumList.add(item);
		}

		List<String> dbNameList=new ArrayList<String>();
		List<String> redisNameList=new ArrayList<String>();

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);


		map.put("interfaceTypeEnumList", interfaceTypeEnumList);
		map.put("serviceList", serviceList);
		map.put("systemList",systemList);
		map.put("requestTypeList",requestTypeList);
		map.put("contentTypeList",contentTypeList);
		map.put("httpTypeList",httpTypeList);
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("dbNameList",dbNameList);
		map.put("redisNameList",redisNameList);
		return "/autotest/interface/" + path;
	}

	@RequestMapping(value="/saveInterface", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveInterface(TestInterfaceDto testInterfaceDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isNotBlank(testInterfaceDto.getName()) && !"null".equals(testInterfaceDto.getName())){
				testInterfaceDto = testInterfaceService.save(testInterfaceDto);
				result.setData(testInterfaceDto);
			} else {
				int code = CommonResultEnum.ERROR.getReturnCode();
				String message ="name不能为空！";
				result.setCode(code);
				result.setMessage(message);
			}
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
