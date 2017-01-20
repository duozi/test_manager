package com.xn.manage.controller;

import com.xn.manage.Enum.DatabaseTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/environment")
public class EnvironmentController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getEnvironmentPage(@PathVariable String  path,ModelMap map) {
		List<DatabaseTypeEnum> databaseTypeEnumList=new ArrayList<DatabaseTypeEnum>();
		for(DatabaseTypeEnum item:DatabaseTypeEnum.values()){
			databaseTypeEnumList.add(item);
		}
		map.put("databaseTypeEnumList",databaseTypeEnumList);
		return "environment/" + path;
	}

}
