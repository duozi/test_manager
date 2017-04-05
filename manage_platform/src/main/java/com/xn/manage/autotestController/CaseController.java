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
import com.xn.interfacetest.entity.RelationCaseDatabase;
import com.xn.interfacetest.entity.RelationCaseParams;
import com.xn.interfacetest.entity.TestInterface;
import com.xn.interfacetest.entity.TestParams;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.*;
import com.xn.manage.utils.JsonValidator;
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

		String interfaceId = request.getParameter("interfaceId");
		map.put("interfaceId",interfaceId);

		//当用例id不为空的时候-查询用例内容
		if(StringUtils.isNotBlank(caseId) && !"null".equals(caseId)){
			searchCaseDetail(Long.parseLong(caseId),map);
		}

		//redis操作类型
		List<RedisOperationTypeEnum> redisOperationTypeEnumList=new ArrayList<RedisOperationTypeEnum>();
		for(RedisOperationTypeEnum item:RedisOperationTypeEnum.values()){
			redisOperationTypeEnumList.add(item);
		}

		//通过接口id查询接口的参数
		if(StringUtils.isNotBlank(interfaceId)){
			List<TestParamsDto> testParamsDtoList = testParamsService.getParamsByInterfaceId(interfaceId);
			map.put("testParamsDtoList",testParamsDtoList);
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

	@RequestMapping(value="/case_item_multiple", method = RequestMethod.GET)
	public String getCaseItemMultiple(HttpServletRequest request, ModelMap map) {
		String caseId = request.getParameter("caseId");
		map.put("caseId",caseId);

		String interfaceId = request.getParameter("interfaceId");
		map.put("interfaceId",interfaceId);

		//通过接口id查询接口的参数
		if(StringUtils.isNotBlank(interfaceId)){
			List<TestParamsDto> testParamsDtoList = testParamsService.getParamsByInterfaceId(interfaceId);
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
	public String getCasePage(HttpServletRequest request, ModelMap map) {
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
		}
		String systemId = request.getParameter("systemId");
		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",Long.parseLong(systemId));
			map.put("systemId",Long.parseLong(systemId));
		}
		String type = request.getParameter("type");
		if(StringUtils.isNotBlank(type) && !"null".equals(type)){
			params.put("type",Integer.parseInt(type));
			map.put("type",Integer.parseInt(type));
		}

		String interfaceId = request.getParameter("interfaceId");
		if(StringUtils.isNotBlank(interfaceId) && !"null".equals(interfaceId)){
			params.put("interfaceId",interfaceId);
			map.put("interfaceId",interfaceId);
		}
		String createPerson = request.getParameter("createPerson");
		if(StringUtils.isNotBlank(createPerson) && !"null".equals(createPerson)){
			params.put("createPerson",createPerson);
			map.put("createPerson",createPerson);
		}

		List<TestCaseDto> testCaseDtoList = testCaseService.listByParams(params);

		map.put("testCaseDtoList",testCaseDtoList);
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
			Long environmentId = 0L;
			if(StringUtils.isNotBlank(environmentIdStr)){
				environmentId = Long.parseLong(environmentIdStr);
			}

			String caseIdStr = request.getParameter("caseId");
			Long caseId = 0L;
			if(StringUtils.isNotBlank(caseIdStr)){
				caseId = Long.parseLong(caseIdStr);
			}

			testCaseService.testRun(caseId,environmentId);
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("执行异常｛｝",e);
		}
		return  result;
	}
}
