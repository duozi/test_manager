package com.xn.manage.autotestController;


import com.xn.common.api.CompanyService;
import com.xn.common.base.CommonResult;
import com.xn.common.utils.FileUtil;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.common.utils.PropertyUtil;
import com.xn.interfacetest.Enum.*;
import com.xn.interfacetest.api.*;
import com.xn.interfacetest.dto.*;
import com.xn.manage.Enum.ParamTypeEnum;
import com.xn.manage.utils.FileUpload;
import com.xn.manage.utils.JsonValidator;
import com.xn.manage.utils.ModelUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/autotest/case")
public class CaseController {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);

	@Autowired
	TestSystemService testSystemService;

	@Autowired
	TestServiceService testServiceService;

	@Autowired
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

	@Autowired
	private TestDatabaseConfigService testDatabaseConfigService;

	@Autowired
	private TestRedisConfigService testRedisConfigService;

	@Autowired
	private RelationCaseDatabaseService relationCaseDatabaseService;

	@Autowired
	private RelationCaseRedisService relationCaseRedisService;

	@Autowired
	private ParamsAssertService paramsAssertService;

	@Autowired
	private DataAssertService dataAssertService;

	@Autowired
	private RedisAssertService redisAssertService;

	@Autowired
	private TestEnvironmentService testEnvironmentService;

	@RequestMapping(value="/case_item", method = RequestMethod.GET)
	public String getCaseItem(HttpServletRequest request, ModelMap map) {
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

		TestCaseDto testCaseDto = testCaseService.get(caseId);
		map.put("testCaseDto",testCaseDto);

		String interfaceId = request.getParameter("interfaceId");
		map.put("interfaceId",interfaceId);

		//当用例id不为空的时候-查询用例内容
		if(StringUtils.isNotBlank(caseId) && !"null".equals(caseId)){
			searchCaseDetail(Long.parseLong(caseId),map);
		}

		if(null != testCaseDto.getInterfaceId()){
			//查询参数列表
			List<TestParamsDto> testParamsDtoList = testParamsService.getParamsByInterfaceId(testCaseDto.getInterfaceId());
			map.put("testParamsDtoListNew",testParamsDtoList);
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

		//查询联调的时候的环境信息
		List<TestEnvironmentDto> testEnvironmentDtoList = testEnvironmentService.list(new TestEnvironmentDto());

		//将页面需要的元素回传给页面
		map.put("testEnvironmentDtoList",testEnvironmentDtoList);
		map.put("paramTypeList",ParamTypeEnum.values());
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("testRedisConfigDtoList", testRedisConfigDtoList);
		map.put("databaseConfigDtoList", databaseConfigDtoList);
		map.put("redisAssertTypeEnumList",RedisAssertTypeEnum.values());
		map.put("redisOperationTypeEnumList",RedisOperationTypeEnum.values());
		map.put("appendParamList",AppendParamEnum.values());
		return "/autotest/case/case_item_single";
	}

	//查询用例详情
	private void searchCaseDetail(Long caseId,ModelMap map){
		TestCaseDto testCaseDto = testCaseService.get(caseId);
		map.put("testCaseDto",testCaseDto);

		//包含该用例的测试集
		List<TestSuitDto> testSuitDtoList = testSuitService.getSuitByCaseId(caseId);
		map.put("testSuitDtoList",testSuitDtoList);

		//查询所有的数据库配置
		List<TestDatabaseConfigDto> testDatabaseConfigDtoList = testDatabaseConfigService.list(new TestDatabaseConfigDto());
		map.put("testDatabaseConfigDtoList",testDatabaseConfigDtoList);

		//查询已添加的参数值
		RelationCaseParamsDto relationCaseParamsDto = new RelationCaseParamsDto();
		relationCaseParamsDto.setCaseId(testCaseDto.getId());
		List<RelationCaseParamsDto> paramsDtoList = relationCaseParamsService.list(relationCaseParamsDto);
		map.put("paramsDtoList",paramsDtoList);

		if(null != testCaseDto.getInterfaceId()){
			//当已经有添加的参数值，就只查询出已有的
			if(null != paramsDtoList && paramsDtoList.size() > 0){
				Long[] paramsIds = new Long[paramsDtoList.size()];
				for(int i=0;i<paramsDtoList.size();i++){
					paramsIds[i] = paramsDtoList.get(i).getParamsId();
				}
				List<TestParamsDto> testParamsDtoList = testParamsService.listByInterfaceAndIds(testCaseDto.getInterfaceId(),paramsIds);
				map.put("testParamsDtoList",testParamsDtoList);
			} else {
				//若没有保存过，就查询出所有未删除的列表供选择
				List<TestParamsDto> testParamsDtoList = testParamsService.listByInterfaceAndIds(testCaseDto.getInterfaceId(),null);
				map.put("testParamsDtoList",testParamsDtoList);
			}
		}

		//查询参数校验列表
		ParamsAssertDto paramsAssertDto = new ParamsAssertDto();
		paramsAssertDto.setCaseId(testCaseDto.getId());
		List<ParamsAssertDto> paramsAssertDtoList = paramsAssertService.list(paramsAssertDto);
		map.put("paramsAssertDtoList",paramsAssertDtoList);

		//查询数据库校验列表
		DataAssertDto dataAssertDto = new DataAssertDto();
		dataAssertDto.setCaseId(testCaseDto.getId());
		List<DataAssertDto> dataAssertDtoList = dataAssertService.list(dataAssertDto);
		map.put("dataAssertDtoList",dataAssertDtoList);

		//查询redis校验列表
		RedisAssertDto redisAssertDto = new RedisAssertDto();
		redisAssertDto.setCaseId(testCaseDto.getId());
		List<RedisAssertDto> redisAssertDtoList = redisAssertService.list(redisAssertDto);
		map.put("redisAssertDtoList",redisAssertDtoList);

		//-------------------------------------------------查询数据准备---------------------------------------
		//查询数据库数据
		RelationCaseDatabaseDto relationCaseDatabaseDto = new RelationCaseDatabaseDto();
		relationCaseDatabaseDto.setCaseId(testCaseDto.getId());
		relationCaseDatabaseDto.setOperateType(1);//数据准备operateType为1
		List<RelationCaseDatabaseDto> prepareDatabaseDtoList = relationCaseDatabaseService.list(relationCaseDatabaseDto);
		map.put("prepareDatabaseDtoList",prepareDatabaseDtoList);

		//查询redis数据
		RelationCaseRedisDto relationCaseRedisDto = new RelationCaseRedisDto();
		relationCaseRedisDto.setCaseId(testCaseDto.getId());
		relationCaseRedisDto.setOperateType(1);//数据准备operateType为1
		List<RelationCaseRedisDto> prepareRedisDtoList = relationCaseRedisService.list(relationCaseRedisDto);
		map.put("prepareRedisDtoList",prepareRedisDtoList);

		//-------------------------------------------------查询数据清除------------------------------------------
		//查询数据库数据
		relationCaseDatabaseDto.setOperateType(2);//数据清除operateType为2
		List<RelationCaseDatabaseDto> clearDatabaseDtoList = relationCaseDatabaseService.list(relationCaseDatabaseDto);
		map.put("clearDatabaseDtoList",clearDatabaseDtoList);

		//查询redis数据
		relationCaseRedisDto.setOperateType(2);//数据清除operateType为2
		List<RelationCaseRedisDto> clearRedisDtoList = relationCaseRedisService.list(relationCaseRedisDto);
		map.put("clearRedisDtoList",clearRedisDtoList);
	}

	@RequestMapping(value="/case_item_multiple", method = RequestMethod.GET)
	public String getCaseItemMultiple(HttpServletRequest request, ModelMap map) {
		String caseId = request.getParameter("caseId");
		map.put("caseId",caseId);

		String interfaceId = request.getParameter("interfaceId");
		map.put("interfaceId",interfaceId);

		//通过接口id查询接口的参数
		if(StringUtils.isNotBlank(interfaceId)){
			List<TestParamsDto> testParamsDtoList = testParamsService.getParamsByInterfaceId(Long.parseLong(interfaceId));
			map.put("testParamsDtoList",testParamsDtoList);
		}

		//参数类型
		map.put("paramTypeList",ParamTypeEnum.values());

		//单个用例
		if(StringUtils.isNotBlank(caseId) && !"null".equals(caseId)){
			TestCaseDto testCaseDto = testCaseService.get(caseId);
			map.put("testCaseDto",testCaseDto);

			//包含该用例的测试集
			List<TestSuitDto> testSuitDtoList = testSuitService.getSuitByCaseId(Long.parseLong(caseId));
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
			List<RelationCaseParamsDto> paramsDtoList = relationCaseParamsService.list(relationCaseParamsDto);
			map.put("paramsDtoList",paramsDtoList);

			//查询参数校验列表
			ParamsAssertDto paramsAssertDto = new ParamsAssertDto();
			paramsAssertDto.setCaseId(testCaseDto.getId());
			List<ParamsAssertDto> paramsAssertDtoList = paramsAssertService.list(paramsAssertDto);
			map.put("paramsAssertDtoList",paramsAssertDtoList);

			//查询数据库校验列表
			DataAssertDto dataAssertDto = new DataAssertDto();
			dataAssertDto.setCaseId(testCaseDto.getId());
			List<DataAssertDto> dataAssertDtoList = dataAssertService.list(dataAssertDto);
			map.put("dataAssertDtoList",dataAssertDtoList);

			//查询redis校验列表
			RedisAssertDto redisAssertDto = new RedisAssertDto();
			redisAssertDto.setCaseId(testCaseDto.getId());
			List<RedisAssertDto> redisAssertDtoList = redisAssertService.list(redisAssertDto);
			map.put("redisAssertDtoList",redisAssertDtoList);

			//-------------------------------------------------查询数据准备---------------------------------------
			//查询数据库数据
			RelationCaseDatabaseDto relationCaseDatabaseDto = new RelationCaseDatabaseDto();
			relationCaseDatabaseDto.setCaseId(testCaseDto.getId());
			relationCaseDatabaseDto.setOperateType(1);//数据准备operateType为1
			List<RelationCaseDatabaseDto> prepareDatabaseDtoList = relationCaseDatabaseService.list(relationCaseDatabaseDto);
			map.put("prepareDatabaseDtoList",prepareDatabaseDtoList);

			//查询redis数据
			RelationCaseRedisDto relationCaseRedisDto = new RelationCaseRedisDto();
			relationCaseRedisDto.setCaseId(testCaseDto.getId());
			relationCaseRedisDto.setOperateType(1);//数据准备operateType为1
			List<RelationCaseRedisDto> prepareRedisDtoList = relationCaseRedisService.list(relationCaseRedisDto);
			map.put("prepareRedisDtoList",prepareRedisDtoList);

			//-------------------------------------------------查询数据清除------------------------------------------
			//查询数据库数据
			relationCaseDatabaseDto.setOperateType(2);//数据清除operateType为2
			List<RelationCaseDatabaseDto> clearDatabaseDtoList = relationCaseDatabaseService.list(relationCaseDatabaseDto);
			map.put("clearDatabaseDtoList",clearDatabaseDtoList);

			//查询redis数据
			relationCaseRedisDto.setOperateType(2);//数据清除operateType为2
			List<RelationCaseRedisDto> clearRedisDtoList = relationCaseRedisService.list(relationCaseRedisDto);
			map.put("clearRedisDtoList",clearRedisDtoList);
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

		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("testRedisConfigDtoList", testRedisConfigDtoList);
		map.put("databaseConfigDtoList", databaseConfigDtoList);
		map.put("redisAssertTypeEnumList",RedisAssertTypeEnum.values());
		map.put("redisOperationTypeEnumList",RedisOperationTypeEnum.values());
		map.put("appendParamList",AppendParamEnum.values());
		return "/autotest/case/case_item_multiple";
	}

	@RequestMapping(value="/case_list", method = RequestMethod.GET)
	public String getCasePage(HttpServletRequest request, ModelMap map,PageInfo pageInfo) {
		StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数

		TestSystemDto systemDto = new TestSystemDto();
		List<TestSystemDto> systemList = testSystemService.list(systemDto );
		map.put("systemList", systemList);

		TestInterfaceDto testInterfaceDto = new TestInterfaceDto();
		List<TestInterfaceDto> interfaceDtoList = testInterfaceService.list(testInterfaceDto );
		map.put("interfaceList", interfaceDtoList);

		Map<String,Object> params = new HashMap<String,Object>();
		String name = request.getParameter("name");
		if(StringUtils.isNotBlank(name) && !"null".equals(name)){
			params.put("name",name);
			map.put("name",name);
			pageParams.append("&name=").append(name);
		}
		String systemId = request.getParameter("systemId");
		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",Long.parseLong(systemId));
			map.put("systemId",Long.parseLong(systemId));
			pageParams.append("&systemId=").append(systemId);
		}
		String type = request.getParameter("type");
		if(StringUtils.isNotBlank(type) && !"null".equals(type)){
			params.put("type",Integer.parseInt(type));
			map.put("type",Integer.parseInt(type));
			pageParams.append("&type=").append(type);
		}

		String interfaceId = request.getParameter("interfaceId");
		if(StringUtils.isNotBlank(interfaceId) && !"null".equals(interfaceId)){
			params.put("interfaceId",interfaceId);
			map.put("interfaceId",interfaceId);
			pageParams.append("&interfaceId=").append(interfaceId);
		}
		String createPerson = request.getParameter("createPerson");
		if(StringUtils.isNotBlank(createPerson) && !"null".equals(createPerson)){
			params.put("createPerson",createPerson);
			map.put("createPerson",createPerson);
			pageParams.append("&createPerson=").append(createPerson);
		}

		if (pageInfo.getCurrentPage() < 1) {
			pageInfo.setCurrentPage(1);
		}
 		pageInfo.setPagination(true);
		pageInfo.setPageSize(10);
		params.put("page", pageInfo);
		pageInfo.setParams(pageParams.toString());

		PageResult<TestCaseDto> result = testCaseService.listByParams(params);

		ModelUtils.setResult(map, result.getPage(), result.getList());
		map.put("caseTypes",CaseTypeEnum.values());
		return "/autotest/case/case_list";
	}

	@RequestMapping(value="/deleteCase", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteCase(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testCaseService.deleteByPK(Long.parseLong(id));
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
			if(null != testCaseDto.getId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(testCaseDto.getId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}


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

			//校验用例编号的唯一性
			List<TestCaseDto> testCaseDtoExist = testCaseService.getByCaseNum(testCaseDto.getNumber());
			if(null != testCaseDtoExist && testCaseDtoExist.size()>0 && testCaseDtoExist.get(0).getId() != testCaseDto.getId()){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("用例编号已存在，请保证用例编号的唯一性");
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
			if(null != testCaseDto.getId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(testCaseDto.getId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}
			testCaseDto.setParamsType(ParamsGroupTypeEnum.CUSTOM.getId());
			testCaseService.updatePart(testCaseDto);
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
	public CommonResult testFormatParams(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		JsonValidator validatorJson = new JsonValidator();
		try{
			String jsonStr = request.getParameter("jsonStr");
			Boolean data = true;
			if(StringUtils.isNotBlank(jsonStr)){
				data = validatorJson.validate(jsonStr);
			}
			result.setData(data);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("校验接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveParams", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveParams(RelationCaseParamsDto relationCaseParamsDto) {
		CommonResult result = new CommonResult();
		try{
			if(null != relationCaseParamsDto.getCaseId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(relationCaseParamsDto.getCaseId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}

			relationCaseParamsDto = relationCaseParamsService.save(relationCaseParamsDto);
			result.setData(relationCaseParamsDto);


		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveParamsValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveParamsValidation(ParamsAssertDto paramsAssertDto) {
		CommonResult result = new CommonResult();
		try{
			if(null != paramsAssertDto.getCaseId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(paramsAssertDto.getCaseId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}

			paramsAssertDto = paramsAssertService.save(paramsAssertDto);
			result.setData(paramsAssertDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveDBValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveDBValidation(DataAssertDto dataAssertDto) {
		CommonResult result = new CommonResult();
		try{
			if(null != dataAssertDto.getCaseId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(dataAssertDto.getCaseId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}

			dataAssertDto = dataAssertService.save(dataAssertDto);
			result.setData(dataAssertDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/saveRedisValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveRedisValidation(RedisAssertDto redisAssertDto) {
		CommonResult result = new CommonResult();
		try{
			if(null != redisAssertDto.getCaseId()) {
				TestCaseDto testCaseDtoNew = testCaseService.get(redisAssertDto.getCaseId());
				if(null != testCaseDtoNew && testCaseDtoNew.getStatus() > PlanStatusEnum.UNPUBLISHED.getId()){
					//该状态不支持修改
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("该用例状态不支持修改！");
					return result;
				}
			}

			redisAssertDto = redisAssertService.save(redisAssertDto);
			result.setData(redisAssertDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteParams", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteParams(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				relationCaseParamsService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteDbValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteDbValidation(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				dataAssertService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteRedisValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteRedisValidation(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				redisAssertService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteParamsValidation", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteParamsValidation(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				paramsAssertService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除异常｛｝",e);
		}
		return  result;
	}

	/**
	 * 联调，测试用例是否可执行
	 * @return
	 */
	@RequestMapping(value="/testRun", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult testRun(HttpServletRequest request){
		CommonResult result = new CommonResult();
		try{
			String environmentIdStr = request.getParameter("environmentId");

			if(!StringUtils.isNotBlank(environmentIdStr)){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请勾选运行环境！");
				return  result;
			}

			String caseIdStr = request.getParameter("caseId");
			if(!StringUtils.isNotBlank(caseIdStr)){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("用例id为空！");
				return  result;
			}

			testCaseService.testRun(Long.parseLong(caseIdStr),Long.parseLong(environmentIdStr));
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("调试出现异常，请联系管理员！");
			logger.error("执行异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value = "/copyCase")
	@ResponseBody
	public CommonResult copyCase(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		Map<String,Object> params = new HashMap<String, Object>();

		String caseId = request.getParameter("caseId");
		if(StringUtils.isNotBlank(caseId) && !"null".equals(caseId)){
			params.put("caseId",caseId);
		} else {
			result.setCode(CommonResultEnum.FAILED.getReturnCode());
			result.setMessage("用例id不能为空");
			return result;
		}

		if(StringUtils.isBlank(request.getParameter("caseNum")) || "null".equals(request.getParameter("caseNum"))){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("请填写用例编号");
			return result;
		}

		String caseNum = request.getParameter("caseNum");
		if(StringUtils.isNotBlank(caseNum) && !"null".equals(caseNum)){
			params.put("caseNum",caseNum);
		}

		//校验用例编号的唯一性
		List<TestCaseDto>testCaseDtoExist = testCaseService.getByCaseNum(request.getParameter("caseNum"));if(null != testCaseDtoExist && testCaseDtoExist.size() > 0){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("用例编号已存在，请保证用例nida编号的唯一性");
			return result;
		}

//		String baseInfo = request.getParameter("baseInfo");
//		if(StringUtils.isNotBlank(baseInfo) && !"null".equals(baseInfo)){
//			params.put("baseInfo",baseInfo);
//		}

		String dataClear = request.getParameter("dataClear");
		if(StringUtils.isNotBlank(dataClear) && !"null".equals(dataClear)){
			params.put("dataClear",dataClear);
		}

		String dataPrepare = request.getParameter("dataPrepare");
		if(StringUtils.isNotBlank(dataPrepare) && !"null".equals(dataPrepare)){
			params.put("dataPrepare",dataPrepare);
		}
		String dataAssert = request.getParameter("dataAssert");
		if(StringUtils.isNotBlank(dataAssert) && !"null".equals(dataAssert)){
			params.put("dataAssert",dataAssert);
		}
		String dataParams = request.getParameter("dataParams");
		if(StringUtils.isNotBlank(dataParams) && !"null".equals(dataParams)){
			params.put("dataParams",dataParams);
		}
		try{
			testCaseService.copyCase(params);
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage("复制用例产生异常，请查看日志" + e.getMessage());
			logger.error("复制用例异常：",e);
		}

		return result;
	}

	@RequestMapping(value="/importCases", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult importCases(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String fileName = file.getOriginalFilename();
			String path = PropertyUtil.getProperty("upload_path") + "excelCases" + File.separator  + fileName;
			//先上传到服务器
			FileUtil.saveFile(file,path);
			//处理excel
			StringBuffer failCaseIds = testCaseService.dealWithExcelFile(path);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("批量导入用例出现异常｛｝",e);
		}
		return  result;
	}


}
