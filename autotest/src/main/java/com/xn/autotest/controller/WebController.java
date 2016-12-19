package com.xn.autotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*")
public class WebController {
	
	@RequestMapping(value="/decorators/{path}", method = RequestMethod.GET)
	public String getDecoratorsPage(@PathVariable String  path) {
		return "decorators/" + path;
	}
	
	@RequestMapping(value="/case/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path) {
		return "case/" + path;
	}
}
