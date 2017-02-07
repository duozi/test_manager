package com.xn.manage.controller;

import com.xn.manage.bean.Department;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@RequestMapping(value="/getDepartment")
	@ResponseBody
	public List<Department> getSystemPage(String id) {
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(new Department(1,"大数据部"));
		departmentList.add(new Department(2,"平台开发部"));
		departmentList.add(new Department(3,"运维质量部"));
		departmentList.add(new Department(4,"应用开发部"));
		departmentList.add(new Department(5,"质量管控与信息安全部"));
		return departmentList;
	}

}
