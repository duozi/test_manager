package com.xn.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xn.manage.Enum.ContentTypeEnum;


@Controller
@RequestMapping("/suit")
public class SuitController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getSuitPage(@PathVariable String  path,ModelMap map) {
		List<ContentTypeEnum> contentTypeList = new ArrayList<ContentTypeEnum>();
		for(ContentTypeEnum item : ContentTypeEnum.values()){
			contentTypeList.add(item);
		}
		map.put("contentTypeList",contentTypeList);  
		return "suit/" + path;  
	}

}
