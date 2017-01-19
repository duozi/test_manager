package com.xn.manage.controller;

import com.xn.manage.Enum.ContentTypeEnum;
import com.xn.manage.Enum.HttpTypeEnum;
import com.xn.manage.Enum.RequestTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/interface")
public class InterfaceController {

	public static void main(String[] args) {

	}

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getPlanPage(@PathVariable String  path ,ModelMap map) {
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
		map.put("requestTypeList",requestTypeList);
		map.put("contentTypeList",contentTypeList);
		map.put("httpTypeList",httpTypeList);
		return "interface/" + path;
	}

}
