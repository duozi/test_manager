package com.xn.manage.autotestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.service.TestEnvironmentService;
import com.xn.manage.Enum.CommonResultEnum;
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

import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.DatabaseTypeEnum;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/autotest/environment")
public class EnvironmentController {
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

	@Autowired
	private TestSystemService systemService;

	@Autowired
	private TestServiceService serviceService;

	@Autowired
	private TestEnvironmentService testEnvironmentService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getEnvironmentmPage(@PathVariable String  path, HttpServletRequest request, ModelMap model) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);

		List<DatabaseTypeEnum> databaseTypeEnumList=new ArrayList<DatabaseTypeEnum>();
		for(DatabaseTypeEnum item:DatabaseTypeEnum.values()){
			databaseTypeEnumList.add(item);
		}

		if("environment_list".equals(path)){
			//如果是列表页，则查询环境list数据
			String selectSystemId = request.getParameter("selectSystemId");
			String environmentName = request.getParameter("environmentName");

			Map<String,Object> params = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(selectSystemId) && !"null".equals(selectSystemId)){
				params.put("systemId",selectSystemId);
				model.put("systemId",selectSystemId);
			}

			if(StringUtils.isNotBlank(environmentName) && !"null".equals(environmentName)){
				params.put("name",environmentName);
				model.put("name",environmentName);
			}

			List<TestEnvironmentDto> environmentDtoList = testEnvironmentService.listWithSystem(params);
			model.put("environmentDtoList",environmentDtoList);
		} else if("environment_item".equals(path)){
			//如果是详情页面，则查询指定环境信息
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id) && !"null".equals(id)){
				TestEnvironmentDto environmentDto = testEnvironmentService.getWithSystem(Long.parseLong(id));
				model.put("environmentDto",environmentDto);
				model.put("id",id);
			}
			String properties = request.getParameter("properties");
		}


		model.put("serviceList", serviceList);
		model.put("systemList", systemList);
		model.put("databaseTypeEnumList",databaseTypeEnumList);


		return "/autotest/environment/" + path;
	}

	@RequestMapping(value="/saveEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveEnvironment(TestEnvironmentDto testEnvironmentDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isNotBlank(testEnvironmentDto.getName()) && !"null".equals(testEnvironmentDto.getName())){
				testEnvironmentService.save(testEnvironmentDto);
			} else {
				int code = CommonResultEnum.PARAM_ERROR.getReturnCode();
				String message ="name不能为空！";
				result.setCode(code);
				result.setMessage(message);
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存环境异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteEnvironment(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testEnvironmentService.deleteByPK(Long.parseLong(id));
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
//
//	@RequestMapping(value="/saveServiceProperties", method = RequestMethod.POST)
//	@ResponseBody
//	public CommonResult saveServiceProperties( testEnvironmentDto) {
//		CommonResult result = new CommonResult();
//		try{
//			if(StringUtils.isNotBlank(testEnvironmentDto.getName()) && !"null".equals(testEnvironmentDto.getName())){
//				testEnvironmentService.save(testEnvironmentDto);
//			} else {
//				int code = CommonResultEnum.PARAM_ERROR.getReturnCode();
//				String message ="name不能为空！";
//				result.setCode(code);
//				result.setMessage(message);
//			}
//		}catch (Exception e){
//			int code = CommonResultEnum.ERROR.getReturnCode();
//			String message =e.getMessage();
//			result.setCode(code);
//			result.setMessage(message);
//			logger.error("保存环境异常｛｝",e);
//		}
//		return  result;
//	}
}
