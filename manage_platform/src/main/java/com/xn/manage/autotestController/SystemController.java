package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;

@Controller
@RequestMapping("/autotest/system")
public class SystemController {
//	@Autowired
//	private TestSystemService systemService;

	@RequestMapping(value="/getSystem")
	@ResponseBody
	public List<TestSystemDto> getSystemPage(String id) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
//		systemList = systemService.list(systemDto);
		return systemList;
	}

}
