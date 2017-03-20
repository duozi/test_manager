package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.TestPlan;
import com.xn.interfacetest.service.*;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.ExcuteTypeEnum;
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

import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.manage.Enum.PlanStatusEnum;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/autotest/plan")
public class PlanController {
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

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

	@RequestMapping(value="/plan_list", method = RequestMethod.GET)
	public String getPlanPage(HttpServletRequest request, ModelMap model) {
		Map<String,Object> params = new HashMap<String,Object>();

//		//公司名称
//		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
//		CompanyDto dto = new CompanyDto();
//		companyList = companyService.list(dto);


		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestSuitDto> testSuitDtoList = new ArrayList<TestSuitDto>();
		TestSuitDto testSuitDto = new TestSuitDto();
		testSuitDtoList = testSuitService.list(testSuitDto);

		List<TestEnvironmentDto> testEnvironmentDtoList = new ArrayList<TestEnvironmentDto>();
		TestEnvironmentDto testEnvironmentDto = new TestEnvironmentDto();
		testEnvironmentDtoList = testEnvironmentService.list(testEnvironmentDto);

//		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
//		DepartmentDto departmentDto = new DepartmentDto();
//		departmentList = departmentService.list(departmentDto);

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
//		model.put("departmentList", departmentList);
//		model.put("companyList", companyList);
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
			testPlanDto = testPlanService.save(testPlanDto);
			result.setData(testPlanDto);

			String suitIds = request.getParameter("suitIds");
			Long suitId = 0L;
			//遍历插入测试集与测试计划的关系
			if(StringUtils.isNotBlank(suitIds)){
				String[] ids = suitIds.split(",|，");
				for(String suitIdStr : ids){
					if(!"null".equals(suitIdStr) && StringUtils.isNotBlank(suitIdStr)){
						RelationPlanSuitDto relationPlanSuitDto = new RelationPlanSuitDto();
						relationPlanSuitDto.setPlanId(testPlanDto.getId());
						relationPlanSuitDto.setSuitId(Long.parseLong(suitIdStr));
						relationPlanSuitService.save(relationPlanSuitDto);
					}
				}

			}

			TimeConfigDto timeConfigDto = new TimeConfigDto();
//			if(){
//
//			}
			String excuteTime =  request.getParameter("excuteTime");
			if(StringUtils.isNotBlank(excuteTime)){
				timeConfigDto.setTimeExpression(excuteTime);
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
