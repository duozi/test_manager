package com.xn.manage.autotestController;

import com.xn.company.dto.CompanyDto;
import com.xn.company.dto.DepartmentDto;
import com.xn.company.service.CompanyService;
import com.xn.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/manage")
public class ManageController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private TestSystemService systemService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSystemPage(@PathVariable String  path, ModelMap model) {
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto dto = new CompanyDto();
		companyList = companyService.list(dto);


		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);


		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
		DepartmentDto departmentDto = new DepartmentDto();
		departmentList = departmentService.list(departmentDto);


		model.put("systemList", systemList);
		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "/autotest/manage/" + path;
	}

}
