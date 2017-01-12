package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/case")
public class CaseController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path, HttpServletRequest request, ModelMap map) {
		String type=request.getParameter("caseType");

		map.put("caseType",type);
		return "case/" + path;
	}

}
