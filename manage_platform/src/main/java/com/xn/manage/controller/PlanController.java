package com.xn.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/plan")
public class PlanController {

	public static void main(String[] args) {

	}

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getPlanPage(@PathVariable String  path) {
		return "plan/" + path;
	}
}
