package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/autotest/manage")
public class ManageController {

	@Resource
	private CompanyService companyService;

	@Resource
	private TestSystemService testSystemService;

	@Resource
	private DepartmentService departmentService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSystemPage(@PathVariable String  path, ModelMap model) {
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto dto = new CompanyDto();
		companyList = companyService.list(dto);


		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = testSystemService.list(systemDto);


		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
		DepartmentDto departmentDto = new DepartmentDto();
		departmentList = departmentService.list(departmentDto);


		model.put("systemList", systemList);
		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "/autotest/manage/" + path;
	}

	@RequestMapping(value="/saveCompany")
	@ResponseBody
	public String saveCompany(@RequestBody CompanyDto companyDto) {

		return "/autotest/manage/" ;
	}


}
