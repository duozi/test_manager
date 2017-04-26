package com.xn.manage.autotestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.interfacetest.api.RelationPlanEnvironmentService;
import com.xn.interfacetest.api.RelationPlanSuitService;
import com.xn.interfacetest.api.TestEnvironmentService;
import com.xn.interfacetest.api.TestPlanService;
import com.xn.interfacetest.api.TestSuitService;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.api.TimeConfigService;
import com.xn.interfacetest.dto.RelationPlanEnvironmentDto;
import com.xn.interfacetest.dto.RelationPlanSuitDto;
import com.xn.interfacetest.dto.TestEnvironmentDto;
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.dto.TimeConfigDto;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.ExcuteTypeEnum;
import com.xn.manage.Enum.PlanStatusEnum;

@Controller
@RequestMapping("/autotest/plan")
public class PlanController {
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	@Autowired
	private CompanyService companyService;

	@Autowired
	private TestSystemService systemService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TestPlanService testPlanService;

	@Autowired
	private RelationPlanSuitService relationPlanSuitService;

	@Autowired
	private TestSuitService testSuitService;

	@Autowired
	private TestEnvironmentService testEnvironmentService;

	@Autowired
	private RelationPlanEnvironmentService relationPlanEnvironmentService;

	@Autowired
	private TimeConfigService timeConfigService;

	@RequestMapping(value="/plan_list", method = RequestMethod.GET)
	public String getPlanPage(HttpServletRequest request, ModelMap model) {
		Map<String,Object> params = new HashMap<String,Object>();

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestSuitDto> testSuitDtoList = new ArrayList<TestSuitDto>();
		TestSuitDto testSuitDto = new TestSuitDto();
		testSuitDtoList = testSuitService.list(testSuitDto);

		List<TestEnvironmentDto> testEnvironmentDtoList = new ArrayList<TestEnvironmentDto>();
		TestEnvironmentDto testEnvironmentDto = new TestEnvironmentDto();
		testEnvironmentDtoList = testEnvironmentService.list(testEnvironmentDto);

		String name = request.getParameter("name");
		if(StringUtils.isNotBlank(name) && !"null".equals(name)){
			params.put("name",name);
			model.put("name",name);
		}

		String createPerson = request.getParameter("createPerson");
		if(StringUtils.isNotBlank(createPerson) && !"null".equals(createPerson)){
			params.put("createPerson",createPerson);
			model.put("createPerson",createPerson);
		}

		String systemId = request.getParameter("systemId");
		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",systemId);
			model.put("systemId",systemId);
		}

		String status = request.getParameter("status");
		if(StringUtils.isNotBlank(status) && !"null".equals(status)){
			params.put("status",status);
			model.put("status",status);
		}

		//测试计划
		List<TestPlanDto> planList = new ArrayList<TestPlanDto>();

		planList = testPlanService.listWithOtherInformation(params);

		model.put("planStatusList",PlanStatusEnum.values());
		model.put("planList",planList);
		model.put("systemList", systemList);
		model.put("testSuitDtoList", testSuitDtoList);
		model.put("excuteList", ExcuteTypeEnum.values());
		model.put("testEnvironmentDtoList",testEnvironmentDtoList);
		return "/autotest/plan/plan_list";
	}

	@RequestMapping(value="/deletePlan", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deletePlan(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testPlanService.deleteByPK(Long.parseLong(id));
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

	@RequestMapping(value="/savePlan", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult savePlan( TestPlanDto testPlanDto,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isBlank(testPlanDto.getName()) || "null".equals(testPlanDto.getName())){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("请填写计划名称");
				return result;
			}

			Map<String,Object> params = new HashMap<String,Object>();
			String[] suitIds = request.getParameterValues("suitIds");
			params.put("suitIds",suitIds);
			String[] excuteTimes =  request.getParameterValues("excuteTime");
			params.put("excuteTime",excuteTimes);
			String timeDescription =  request.getParameter("timeDescription");
			params.put("timeDescription",timeDescription);
			String cronExpression =  request.getParameter("cronExpression");
			params.put("cronExpression",cronExpression);
			String[] environmentIds =  request.getParameterValues("environmentIds");
			params.put("environmentIds",environmentIds);

			result = testPlanService.saveWithInfo(testPlanDto,params);

		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("保存接口异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/publishPlan", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult publishPlan(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testPlanService.changeStatusOfPlan(PlanStatusEnum.PUBLISHED.getId(),Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message = e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("发布操作异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/getPlanTime", method = RequestMethod.POST)
	@ResponseBody
	public List<TimeConfigDto> getPlanTime(HttpServletRequest request) {
		String planId = request.getParameter("planId");
		List<TimeConfigDto> timeConfigDtos = new ArrayList<TimeConfigDto>();
		try{
			if(StringUtils.isNotBlank(planId)){
				timeConfigDtos = timeConfigService.getByPlanId(Long.parseLong(planId));
			}
		}catch (Exception e){
			logger.error("操作异常｛｝",e);
		}
		return  timeConfigDtos;
	}

	@RequestMapping(value="/savePlanSuit", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult savePlanSuit(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String[] suitIds = request.getParameterValues("suitIds[]");
			Long suitId = 0L;
			//先删除对应计划的所有测试集，再保存新的测试集关系
			String planIdStr = request.getParameter("planId");
			Long planId = Long.parseLong(planIdStr);
			relationPlanSuitService.deleteByPlanId(planId);
			if(null != suitIds && suitIds.length > 0){
				for(String suitIdStr : suitIds){
					if(!"null".equals(suitIdStr) && StringUtils.isNotBlank(suitIdStr)){
						RelationPlanSuitDto relationPlanSuitDto = new RelationPlanSuitDto();
						relationPlanSuitDto.setPlanId(planId
						);
						relationPlanSuitDto.setSuitId(Long.parseLong(suitIdStr));
						relationPlanSuitService.save(relationPlanSuitDto);
					}
				}
			}

		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("保存测试集异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/savePlanEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult savePlanEnvironment(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String[] environmentIds = request.getParameterValues("environmentIds[]");
			Long suitId = 0L;
			//先删除对应计划的所有测试集，再保存新的测试集关系
			String planIdStr = request.getParameter("planId");
			Long planId = Long.parseLong(planIdStr);
			//先删除对应计划的所有环境关系，再保存新的测试集关系
			relationPlanEnvironmentService.deleteByPlanId(planId);
			if(null != environmentIds && environmentIds.length > 0){
				for(String environmentIdStr : environmentIds){
					if(!"null".equals(environmentIdStr) && StringUtils.isNotBlank(environmentIdStr)){
						RelationPlanEnvironmentDto relationPlanEnvironmentDto = new RelationPlanEnvironmentDto();
						relationPlanEnvironmentDto.setPlanId(planId);
						relationPlanEnvironmentDto.setEnvironmentId(Long.parseLong(environmentIdStr));
						relationPlanEnvironmentService.save(relationPlanEnvironmentDto);
					}
				}
			}
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("保存测试环境异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/savePlanTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult savePlanTime(TimeConfigDto timeConfigDto,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			//先删除以前的配置
			timeConfigService.deleteByPlanId(timeConfigDto.getPlanId());
			String excuteTypeStr = request.getParameter("excuteType");
			Integer excuteType = Integer.parseInt(excuteTypeStr);
			if(excuteType == ExcuteTypeEnum.once.getId()){
				String[] excuteTimes =  request.getParameterValues("excuteTime");
				if(null != excuteTimes && excuteTimes.length > 0){
					for(String excuteTime : excuteTimes){
						boolean dateflag=true;
						Date time = new Date();
						try {
							time = format.parse(excuteTime);
						}catch (ParseException e){
							dateflag=false;
						}finally{
							if(!dateflag){
								result.setCode(CommonResultEnum.ERROR.getReturnCode());
								result.setMessage("时间格式不正确！");
								return result;
							}
							timeConfigDto.setExcuteTime(excuteTime);
							timeConfigService.save(timeConfigDto);
						}
					}
				}
			} else if(excuteType == ExcuteTypeEnum.circulation.getId()){
				String timeDescription =  request.getParameter("timeDescription");
				if(StringUtils.isNotBlank(timeDescription)){
					timeConfigDto.setDescription(timeDescription);
				}

				String cronExpression =  request.getParameter("cronExpression");
				if(StringUtils.isNotBlank(cronExpression)){
					timeConfigDto.setTimeExpression(cronExpression);
				}
				timeConfigService.save(timeConfigDto);
			}
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("保存测试集异常｛｝",e);
		}
		return  result;
	}

	//执行测试计划
	@RequestMapping(value="/excutePlan", method = RequestMethod.POST)
	@ResponseBody
	public com.xn.common.base.CommonResult excutePlan(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try{
			String planIdStr = request.getParameter("planId");
			if(StringUtils.isBlank(planIdStr)){
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("计划id为空");
				return result;
			}

			Long planId = Long.parseLong(planIdStr);
			result = testPlanService.excutePlan(planId);
		}catch (Exception e){
			result.setCode(CommonResultEnum.ERROR.getReturnCode());
			result.setMessage(e.getMessage());
			logger.error("计划执行异常｛｝",e);
		}
		return  result;
	}
}
