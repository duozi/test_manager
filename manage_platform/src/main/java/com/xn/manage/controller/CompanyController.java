package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSystemPage(@PathVariable String  path) {
		return "company/" + path;
	}

}
