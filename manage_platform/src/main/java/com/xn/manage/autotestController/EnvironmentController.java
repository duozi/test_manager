package com.xn.manage.autotestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.RelationServiceEnvironment;
import com.xn.interfacetest.entity.TestEnvironment;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.performance.util.CommonResult;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@Autowired
	private RelationServiceEnvironmentService relationServiceEnvironmentService;

	@Autowired
	private RelationDatabaseEnvironmentService relationDatabaseEnvironmentService;

	@Autowired
	private TestDatabaseConfigService testDatabaseConfigService;

	@Autowired
	private TestRedisConfigService testRedisConfigService;

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
				//查询环境和系统信息
				TestEnvironmentDto environmentDto = testEnvironmentService.getWithSystem(Long.parseLong(id));
				model.put("environmentDto",environmentDto);
				model.put("id",id);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("environmentId",id);
				//查询环境对应的配置信息
				if(environmentDto.getServiceProperty()==1){
					//如果有服务配置则查询服务配置信息
					List<RelationServiceEnvironmentDto> relationServiceEnvironmentDtoList = relationServiceEnvironmentService.list(params);
					model.put("relationServiceEnvironmentDtoList",relationServiceEnvironmentDtoList);
				}
				if(environmentDto.getDbProperty()==1){
					//如果有数据库配置则查询数据库配置信息
					List<TestDatabaseConfigDto> testDatabaseConfigDtoList = testDatabaseConfigService.list(params);
					model.put("testDatabaseConfigDtoList",testDatabaseConfigDtoList);
				}
				if(environmentDto.getRedisProperty()==1){
					//如果有redis配置则查询redis配置信息
					List<TestRedisConfigDto> testRedisConfigDtoList = testRedisConfigService.list(params);
					model.put("testRedisConfigDtoList",testRedisConfigDtoList);
				}
			}
		}


		model.put("serviceList", serviceList);
		model.put("systemList", systemList);
		model.put("databaseTypeEnumList",databaseTypeEnumList);

		return "/autotest/environment/" + path;
	}

	/**
	 * 保存环境基本信息
	 * @param testEnvironmentDto
	 * @return
	 */
	@RequestMapping(value="/saveEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveEnvironment(TestEnvironmentDto testEnvironmentDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testEnvironmentDto.getName()) || "null".equals(testEnvironmentDto.getName())){
				int code = CommonResultEnum.ERROR.getReturnCode();
				String message ="name不能为空！";
				result.setCode(code);
				result.setMessage(message);
				return  result;
			}

			if(null == testEnvironmentDto.getSystemId()){
				int code = CommonResultEnum.ERROR.getReturnCode();
				String message ="请选择系统！";
				result.setCode(code);
				result.setMessage(message);
				return  result;
			}

			testEnvironmentDto = testEnvironmentService.save(testEnvironmentDto);
			result.setData(testEnvironmentDto);
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

	/**
	 * 删除服务配置信息，单条删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteServiceEnvironmentProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteServiceEnvironmentProperties(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				relationServiceEnvironmentService.deleteByPK(Long.parseLong(id));
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
	 * 删除db配置信息，单条删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteDBProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteDBProperties(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testDatabaseConfigService.deleteByPK(Long.parseLong(id));
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
	 * 删除redis配置信息，单条删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteRedisProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteRedisProperties(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testRedisConfigService.deleteByPK(Long.parseLong(id));
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
	 * 保存数据库配置信息
	 * @param testDatabaseConfigDto
	 * @return
	 */
	@RequestMapping(value="/saveDBProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveDBProperties(TestDatabaseConfigDto testDatabaseConfigDto) {
		CommonResult result = new CommonResult();
		try{
			//字段校验
			if(null == testDatabaseConfigDto.getEnvironmentId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请先保存环境基础信息！");
				return  result;
			}

			if(null == testDatabaseConfigDto.getIpAddress()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("ip不能为空！");
				return  result;
			}

			if(null == testDatabaseConfigDto.getType()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请选择数据库类型！");
				return  result;
			}

			if(null == testDatabaseConfigDto.getPortAddress()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("端口号不能为空！");
				return  result;
			}

			if(null == testDatabaseConfigDto.getDatabaseName()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("数据库名不能为空！");
				return  result;
			}

			if(null == testDatabaseConfigDto.getName()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("数据库配置名不能为空！");
				return  result;
			}


			if(null == testDatabaseConfigDto.getUserName()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("数据库用户名不能为空！");
				return  result;
			}


			if(null == testDatabaseConfigDto.getPassword()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("数据库密码不能为空！");
				return  result;
			}
			//如果查询到没有保存设置选项则保存一次
			TestEnvironmentDto environmentDto = testEnvironmentService.get(testDatabaseConfigDto.getEnvironmentId());
			if(null != environmentDto && environmentDto.getDbProperty() == 0){
				environmentDto.setDbProperty(1);
				testEnvironmentService.update(environmentDto);
			}
			testDatabaseConfigDto = testDatabaseConfigService.save(testDatabaseConfigDto);
			result.setData(testDatabaseConfigDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存服务环境异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 保存redis配置信息
	 * @param testRedisConfigDto
	 * @return
	 */
	@RequestMapping(value="/saveRedisProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveRedisProperties(TestRedisConfigDto testRedisConfigDto) {
		CommonResult result = new CommonResult();
		try{

			if(null == testRedisConfigDto.getEnvironmentId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请先保存环境基础信息！");
				return  result;
			}

			if(null == testRedisConfigDto.getName()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("redis名称不能为空！");
				return  result;
			}

			if(null == testRedisConfigDto.getIpAddress()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("集群ip地址不能为空！");
				return  result;
			}

			if(null == testRedisConfigDto.getTimeout()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("超时时间不能为空！");
				return  result;
			}

			if(null == testRedisConfigDto.getTryTime()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("重试次数不能为空！");
				return  result;
			}
			//如果查询到没有保存设置选项则保存一次
			TestEnvironmentDto environmentDto = testEnvironmentService.get(testRedisConfigDto.getEnvironmentId());
			if(null != environmentDto && environmentDto.getRedisProperty() == 0){
				environmentDto.setRedisProperty(1);
				testEnvironmentService.update(environmentDto);
			}
			testRedisConfigDto = testRedisConfigService.save(testRedisConfigDto);
			result.setData(testRedisConfigDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存服务环境异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 保存服务配置信息
	 * @param relationServiceEnvironmentDto
	 * @return
	 */
	@RequestMapping(value="/saveServiceProperties", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveServiceProperties(RelationServiceEnvironmentDto relationServiceEnvironmentDto) {
		CommonResult result = new CommonResult();
		try{

			if(null == relationServiceEnvironmentDto.getEnvironmentId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请先保存环境基础信息！");
				return  result;
			}

			if(null == relationServiceEnvironmentDto.getServiceId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("服务不能为空！");
				return  result;
			}

			if(null == relationServiceEnvironmentDto.getIpAddress()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("ip地址不能为空！");
				return  result;
			}

			String httpPort = relationServiceEnvironmentDto.getHttpPort();
			if(null != httpPort && !StringUtils.isNumeric(httpPort)){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("端口号应该为数字！");
				return  result;
			}

			String dubboPort = relationServiceEnvironmentDto.getDubboPort();
			if(null != dubboPort && !StringUtils.isNumeric(dubboPort)){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("端口号应该为数字！");
				return  result;
			}

			//如果查询到没有保存设置选项则保存一次
			TestEnvironmentDto environmentDto = testEnvironmentService.get(relationServiceEnvironmentDto.getEnvironmentId());
			if(null != environmentDto && environmentDto.getServiceProperty() == 0){
				environmentDto.setServiceProperty(1);
				testEnvironmentService.update(environmentDto);
			}

			relationServiceEnvironmentDto = relationServiceEnvironmentService.save(relationServiceEnvironmentDto);
			result.setData(relationServiceEnvironmentDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存服务环境异常｛｝",e);
		}
		return  result;
	}
}
