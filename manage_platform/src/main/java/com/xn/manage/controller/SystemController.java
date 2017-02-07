package com.xn.manage.controller;

import com.xn.manage.bean.System;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemController {

	@RequestMapping(value="/getSystem")
	@ResponseBody
	public List<System> getSystemPage(String id) {
		List<System> systemList = new ArrayList<System>();
		systemList.add(new System(1,"风控规则"));
		systemList.add(new System(2,"支付中心"));
		systemList.add(new System(3,"征信公司"));
		systemList.add(new System(4,"商户平台"));
		return systemList;
	}

}
