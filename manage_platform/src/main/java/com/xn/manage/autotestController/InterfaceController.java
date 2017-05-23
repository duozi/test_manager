package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.Enum.*;
import com.xn.interfacetest.Enum.ContentTypeEnum;
import com.xn.interfacetest.dto.*;
import com.xn.manage.Enum.*;
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

import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.interfacetest.api.RelationCaseDatabaseService;
import com.xn.interfacetest.api.RelationCaseRedisService;
import com.xn.interfacetest.api.TestDatabaseConfigService;
import com.xn.interfacetest.api.TestInterfaceService;
import com.xn.interfacetest.api.TestRedisConfigService;
import com.xn.interfacetest.api.TestServiceService;
import com.xn.interfacetest.api.TestSystemService;

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
		map.put("contentTypeList", ContentTypeEnum.values());

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
	public String getInterfacePage(HttpServletRequest request, ModelMap map, PageInfo pageInfo) {
		StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);
		map.put("serviceList", serviceList);

		Map<String,Object> params = new HashMap<String,Object>();
		String interfaceName = request.getParameter("name");
		if(StringUtils.isNotBlank(interfaceName) && !"null".equals(interfaceName)){
			params.put("name",interfaceName);
			map.put("name",interfaceName);
			pageParams.append("&name=").append(interfaceName);
		}
		String serviceId = request.getParameter("serviceId");
		if(StringUtils.isNotBlank(serviceId) && !"null".equals(serviceId)){
			params.put("serviceId",Long.parseLong(serviceId));
			map.put("serviceId",Long.parseLong(serviceId));
			pageParams.append("&serviceId=").append(serviceId);
		}
		String type = request.getParameter("type");
		if(StringUtils.isNotBlank(type) && !"null".equals(type)){
			params.put("type",Integer.parseInt(type));
			map.put("type",Integer.parseInt(type));
			pageParams.append("&type=").append(type);
		}

		if (pageInfo.getCurrentPage() < 1) {
			pageInfo.setCurrentPage(1);
		}
		pageInfo.setPagination(true);
		pageInfo.setPageSize(10);
		params.put("page", pageInfo);
		pageInfo.setParams(pageParams.toString());
		PageResult<TestInterfaceDto> result = testInterfaceService.listByParams(params);

		ModelUtils.setResult(map, result.getPage(), result.getList());

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
			if(null != testInterfaceDto.getId()) {
				TestInterfaceDto testInterfaceDtoNew = testInterfaceService.get(testInterfaceDto.getId());
				if(null != testInterfaceDtoNew && testInterfaceDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该接口状态不支持修改！");
					return result;
				}
			}

			if(StringUtils.isBlank(testInterfaceDto.getName()) || "null".equals(testInterfaceDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写接口名称");
				return result;
			}

			if(testInterfaceDto.getType() == InterfaceTypeEnum.HTTP.getId()){
				if(StringUtils.isBlank(testInterfaceDto.getContentType()) || "null".equals(testInterfaceDto.getContentType())){
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("请填写ContentType名称");
					return result;
				}
			}

			//校验接口名称唯一，同一个服务的接口名称不能重复
			TestInterfaceDto exist = testInterfaceService.getByServiceIdAndInterfaceName(testInterfaceDto.getServiceId(),testInterfaceDto.getName());
			if(null != exist && exist.getId() != testInterfaceDto.getId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("同一个服务的接口名称不能重复");
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
				TestInterfaceDto testInterfaceDtoNew = testInterfaceService.get(Long.parseLong(id));
				if(null != testInterfaceDtoNew && testInterfaceDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该接口状态不支持删除！");
					return result;
				}
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

			if(null != relationCaseDatabaseDto.getInterfaceId()) {
				TestInterfaceDto testInterfaceDtoNew = testInterfaceService.get(relationCaseDatabaseDto.getInterfaceId());
				if(null != testInterfaceDtoNew && testInterfaceDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该接口状态不支持修改！");
					return result;
				}
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

			if(null != relationCaseRedisDto.getInterfaceId()) {
				TestInterfaceDto testInterfaceDtoNew = testInterfaceService.get(relationCaseRedisDto.getInterfaceId());
				if(null != testInterfaceDtoNew && testInterfaceDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该接口状态不支持修改！");
					return result;
				}
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

			String caseId = request.getParameter("caseId");
			if(StringUtils.isNotBlank(caseId)){
				params.put("caseId",caseId);
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

			String caseId = request.getParameter("caseId");
			if(StringUtils.isNotBlank(caseId)){
				params.put("caseId",caseId);
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
