package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.RelationCaseDatabase;
import com.xn.interfacetest.entity.TestDatabaseConfig;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.*;
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

import com.xn.common.company.service.DepartmentService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private TestDatabaseConfigService testDatabaseConfigService;

	@Autowired
	private TestRedisConfigService testRedisConfigService;

	@Autowired
	private RelationCaseDatabaseService relationCaseDatabaseService;

	@Autowired
	private RelationCaseRedisService relationCaseRedisService;

	@RequestMapping(value="/interface_item", method = RequestMethod.GET)
	public String getInterfaceItem(HttpServletRequest request, ModelMap map) {
		//获取http的contentType类型
		List<ContentTypeEnum> contentTypeList = new ArrayList<ContentTypeEnum>();
		for(ContentTypeEnum item : ContentTypeEnum.values()){
			contentTypeList.add(item);
		}

		//http类型--http，https
		List<HttpTypeEnum> httpTypeList = new ArrayList<HttpTypeEnum>();
		for(HttpTypeEnum item : HttpTypeEnum.values()){
			httpTypeList.add(item);
		}

		//请求类型--get，post
		List<RequestTypeEnum> requestTypeList = new ArrayList<RequestTypeEnum>();
		for(RequestTypeEnum item : RequestTypeEnum.values()){
			requestTypeList.add(item);
		}

		//redis操作类型
		List<RedisOperationTypeEnum> redisOperationTypeEnumList=new ArrayList<RedisOperationTypeEnum>();
		for(RedisOperationTypeEnum item:RedisOperationTypeEnum.values()){
			redisOperationTypeEnumList.add(item);
		}

		//数据库配置
		TestDatabaseConfigDto databaseConfigDto = new TestDatabaseConfigDto();
		List<TestDatabaseConfigDto> databaseConfigDtoList = testDatabaseConfigService.list(databaseConfigDto);

		//redis配置
		TestRedisConfigDto redisConfigDto = new TestRedisConfigDto();
		List<TestRedisConfigDto> testRedisConfigDtoList = testRedisConfigService.list(redisConfigDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);

		map.put("serviceList", serviceList);
		map.put("requestTypeList",requestTypeList);
		map.put("contentTypeList",contentTypeList);
		map.put("httpTypeList",httpTypeList);
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("testRedisConfigDtoList", testRedisConfigDtoList);
		map.put("databaseConfigDtoList", databaseConfigDtoList);

		//接口类型--http，dubbo
		List<InterfaceTypeEnum> interfaceTypeEnumList=new ArrayList<InterfaceTypeEnum>();
		for(InterfaceTypeEnum item: InterfaceTypeEnum.values()){
			interfaceTypeEnumList.add(item);
		}
		map.put("interfaceTypeEnumList", interfaceTypeEnumList);

		//查询指定id的接口
		String idStr = request.getParameter("id");
		Long id = null;
		if(StringUtils.isNotBlank(idStr)){
			id = Long.parseLong(idStr);
		}
		TestInterfaceDto testInterfaceDto = testInterfaceService.get(id);
		map.put("interfaceDto", testInterfaceDto);
		return "/autotest/interface/interface_item";
	}

	@RequestMapping(value="/interface_list", method = RequestMethod.GET)
	public String getInterfacePage(HttpServletRequest request, ModelMap map) {
		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);
		map.put("serviceList", serviceList);

		Map<String,Object> params = new HashMap<String,Object>();
		String interfaceName = request.getParameter("name");
		if(StringUtils.isNotBlank(interfaceName) && !"null".equals(interfaceName)){
			params.put("name",interfaceName);
			map.put("name",interfaceName);
		}
		String serviceId = request.getParameter("serviceId");
		if(StringUtils.isNotBlank(serviceId) && !"null".equals(serviceId)){
			params.put("serviceId",Long.parseLong(serviceId));
			map.put("serviceId",Long.parseLong(serviceId));
		}
		String type = request.getParameter("type");
		if(StringUtils.isNotBlank(type) && !"null".equals(type)){
			params.put("type",Integer.parseInt(type));
			map.put("type",Integer.parseInt(type));
		}
		List<TestInterfaceDto> testInterfaceDtoList = testInterfaceService.listByParams(params);

		map.put("testInterfaceDtoList",testInterfaceDtoList);


		//接口类型--http，dubbo
		List<InterfaceTypeEnum> interfaceTypeEnumList=new ArrayList<InterfaceTypeEnum>();
		for(InterfaceTypeEnum item: InterfaceTypeEnum.values()){
			interfaceTypeEnumList.add(item);
		}

		//请求类型--get，post
		List<RequestTypeEnum> requestTypeList = new ArrayList<RequestTypeEnum>();
		for(RequestTypeEnum item : RequestTypeEnum.values()){
			requestTypeList.add(item);
		}

		map.put("requestTypeList",requestTypeList);
		map.put("interfaceTypeList", interfaceTypeEnumList);
		return "/autotest/interface/interface_list";
	}

	@RequestMapping(value="/saveInterface", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveInterface(TestInterfaceDto testInterfaceDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testInterfaceDto.getName()) || "null".equals(testInterfaceDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写接口名称");
				return result;
			}
			testInterfaceDto = testInterfaceService.save(testInterfaceDto);
			result.setData(testInterfaceDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteInterface", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteInterface(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testInterfaceService.deleteByPK(Long.parseLong(id));
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


	@RequestMapping(value="/saveDataBaseSql", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveDataBaseSql(RelationCaseDatabaseDto relationCaseDatabaseDto) {
		CommonResult result = new CommonResult();
		try{

			if(relationCaseDatabaseDto.getInterfaceId() == null){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请先保存接口信息！");
				return  result;
			}
			if(StringUtils.isBlank(relationCaseDatabaseDto.getSqlStr()) || "null".equals(relationCaseDatabaseDto.getSqlStr())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写sql语句！");
				return  result;
			}
			relationCaseDatabaseDto = relationCaseDatabaseService.save(relationCaseDatabaseDto);
			result.setData(relationCaseDatabaseDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveRedisOperation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveRedisOperation(RelationCaseRedisDto relationCaseRedisDto) {
		CommonResult result = new CommonResult();
		try{

			if(relationCaseRedisDto.getInterfaceId() == null){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请先保存接口信息！");
				return  result;
			}

			if(StringUtils.isBlank(relationCaseRedisDto.getKey()) || "null".equals(relationCaseRedisDto.getKey())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("key不能为空！");
				return  result;
			}
			relationCaseRedisDto = relationCaseRedisService.save(relationCaseRedisDto);
			result.setData(relationCaseRedisDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存redis操作异常｛｝",e);
		}
		return  result;
	}


	@RequestMapping(value="/getDataBaseDataByOperateType", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult getDataBaseDataByOperateType(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		Map<String,Object> params = new HashMap<String,Object>();
		try{
			String interfaceId = request.getParameter("interfaceId");
			if(StringUtils.isNotBlank(interfaceId)){
				params.put("interfaceId",interfaceId);
			}

			String operateType = request.getParameter("operateType");
			if(StringUtils.isNotBlank(operateType)){
				params.put("operateType",operateType);
			}
			List<RelationCaseDatabaseDto> relationCaseDatabaseDtoList = relationCaseDatabaseService.list(params);
			result.setData(relationCaseDatabaseDtoList);
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("数据库查询异常");
			logger.error("操作异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/getRedisDataByOperateType", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult getRedisDataByOperateType(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		Map<String,Object> params = new HashMap<String,Object>();
		try{
			String interfaceId = request.getParameter("interfaceId");
			if(StringUtils.isNotBlank(interfaceId)){
				params.put("interfaceId",interfaceId);
			}

			String operateType = request.getParameter("operateType");
			if(StringUtils.isNotBlank(operateType)){
				params.put("operateType",operateType);
			}
			List<RelationCaseRedisDto> relationCaseRedisDtoList = relationCaseRedisService.list(params);
			result.setData(relationCaseRedisDtoList);
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("redis查询异常");
			logger.error("操作异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 删除db配置信息，单条删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteDatabaseData", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteDatabaseData(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				relationCaseDatabaseService.deleteByPK(Long.parseLong(id));
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
	@RequestMapping(value="/deleteRedisData", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteRedisData(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				relationCaseRedisService.deleteByPK(Long.parseLong(id));
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
}
