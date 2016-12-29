package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/environment")
public class EnvironmentController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getEnvironmentmPage(@PathVariable String  path) {
		return "environment/" + path;
	}

}
