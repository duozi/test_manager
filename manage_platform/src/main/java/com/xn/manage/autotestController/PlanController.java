package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

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
import com.xn.interfacetest.dto.TestPlanDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestPlanService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.PlanStatusEnum;

@Controller
@RequestMapping("/autotest/plan")
public class PlanController {

//	@Autowired
//	private CompanyService companyService;
//
//	@Autowired
//	private TestSystemService systemService;
//
//	@Autowired
//	private DepartmentService departmentService;
//
//	@Autowired
//	private TestPlanService planService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getPlanPage(@PathVariable String  path, ModelMap model) {
		List<PlanStatusEnum> planStatusEnumList=new ArrayList<PlanStatusEnum>();
		for(PlanStatusEnum item:PlanStatusEnum.values()){
			planStatusEnumList.add(item);
		}
		model.put("planStatusEnumList",planStatusEnumList);

//		//公司名称
//		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
//		CompanyDto dto = new CompanyDto();
//		companyList = companyService.list(dto);
//
//
//		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
//		TestSystemDto systemDto = new TestSystemDto();
//		systemList = systemService.list(systemDto);


//		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
//		DepartmentDto departmentDto = new DepartmentDto();
//		departmentList = departmentService.list(departmentDto);


		//计划状态
		List<PlanStatusEnum> planStatusList = new ArrayList<PlanStatusEnum>();
		for(PlanStatusEnum item : PlanStatusEnum.values()){
			planStatusList.add(item);
		}

//		//测试计划
//		List<TestPlanDto> planList = new ArrayList<TestPlanDto>();
//		TestPlanDto planDto = new TestPlanDto();
//		planList = planService.list(planDto);
//
//		model.put("planStatusList",planStatusList);
//		model.put("planList",planList);
//		model.put("systemList", systemList);
//		model.put("departmentList", departmentList);
//		model.put("companyList", companyList);
		return "/autotest/plan/" + path;
	}
}
