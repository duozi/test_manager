package com.xn.manage.autotestController;

import com.xn.manage.bean.Company;
import com.xn.manage.bean.Department;
import com.xn.manage.bean.Service;
import com.xn.manage.bean.System;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/service")
public class ServiceController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSystemPage(@PathVariable String  path, ModelMap model) {
		List<Company> companyList = new ArrayList<Company>();
		companyList.add(new Company(1,"牛鼎丰"));
		companyList.add(new Company(2,"小牛集团"));
		companyList.add(new Company(3,"小牛在线"));
		companyList.add(new Company(4,"小牛普惠"));
		companyList.add(new Company(5,"新财富"));
		companyList.add(new Company(6,"钱罐子"));

		List<System> systemList = new ArrayList<System>();
		systemList.add(new System(1,"风控规则"));
		systemList.add(new System(2,"支付中心"));
		systemList.add(new System(3,"征信公司"));
		systemList.add(new System(4,"商户平台"));

		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(new Department(1,"大数据部"));
		departmentList.add(new Department(2,"平台开发部"));
		departmentList.add(new Department(3,"运维质量部"));
		departmentList.add(new Department(4,"应用开发部"));
		departmentList.add(new Department(5,"质量管控与信息安全部"));

		List<Service> serviceList = new ArrayList<Service>();
		serviceList.add(new Service(1,"ruleengineService"));
		serviceList.add(new Service(2,"riskWeb"));
		serviceList.add(new Service(3,"huaZhengCreditService"));

		model.put("serviceList", serviceList);
		model.put("systemList", systemList);
		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "autotestService/" + path;
	}

}
