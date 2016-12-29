package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getReportPage(@PathVariable String  path) {
		return "report/" + path;
	}

}
