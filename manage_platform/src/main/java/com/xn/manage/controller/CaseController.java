package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/case")
public class CaseController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path) {
		return "case/" + path;
	}

}
