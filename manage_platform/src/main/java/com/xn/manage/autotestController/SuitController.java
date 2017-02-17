package com.xn.manage.autotestController;

import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.ContentTypeEnum;
import com.xn.manage.Enum.HttpTypeEnum;
import com.xn.manage.Enum.RequestTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/autotest/suit")
public class SuitController {
	@Autowired
	private TestServiceService serviceService;

	@Autowired
	private TestSystemService systemService;
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSuitPage(@PathVariable String  path, ModelMap map) {
		List<ContentTypeEnum> contentTypeList = new ArrayList<ContentTypeEnum>();
		for(ContentTypeEnum item : ContentTypeEnum.values()){
			contentTypeList.add(item);
		}

		List<HttpTypeEnum> httpTypeList = new ArrayList<HttpTypeEnum>();
		for(HttpTypeEnum item : HttpTypeEnum.values()){
			httpTypeList.add(item);
		}

		List<RequestTypeEnum> requestTypeList = new ArrayList<RequestTypeEnum>();
		for(RequestTypeEnum item : RequestTypeEnum.values()){
			requestTypeList.add(item);
		}

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = systemService.list(systemDto);

		List<TestServiceDto> serviceList = new ArrayList<TestServiceDto>();
		TestServiceDto serviceDto = new TestServiceDto();
		serviceList = serviceService.list(serviceDto);

		map.put("serviceList", serviceList);
		map.put("systemList",systemList);
		map.put("requestTypeList",requestTypeList);
		map.put("contentTypeList",contentTypeList);
		map.put("httpTypeList",httpTypeList);
		return "/autotest/suit/" + path;
	}

}
