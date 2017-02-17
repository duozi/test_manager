package com.xn.manage.autotestController;

import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.DatabaseTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/environment")
public class EnvironmentController {
	@Autowired
	private TestSystemService systemService;

	@Autowired
	private TestServiceService serviceService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getEnvironmentmPage(@PathVariable String  path, ModelMap model) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);

		List<DatabaseTypeEnum> databaseTypeEnumList=new ArrayList<DatabaseTypeEnum>();
		for(DatabaseTypeEnum item:DatabaseTypeEnum.values()){
			databaseTypeEnumList.add(item);
		}

		model.put("serviceList", serviceList);
		model.put("systemList", systemList);
		model.put("databaseTypeEnumList",databaseTypeEnumList);

		return "/autotest/environment/" + path;
	}

}
