package com.xn.manage.controller;

import com.xn.manage.Enum.ParamTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/case")
public class CaseController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path, HttpServletRequest request, ModelMap map) {
		String type=request.getParameter("caseType");

		map.put("caseType",type);

		List<ParamTypeEnum> paramTypeList = new ArrayList<ParamTypeEnum>();

		for(ParamTypeEnum item : ParamTypeEnum.values()){
			paramTypeList.add(item);
		}
		map.put("paramTypeList",paramTypeList);
		return "case/" + path;
	}

}
