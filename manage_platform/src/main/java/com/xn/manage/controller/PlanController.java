package com.xn.manage.controller;

import com.xn.manage.Enum.PlanStatusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/plan")
public class PlanController {

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getPlanPage(@PathVariable String  path,ModelMap map) {
		List<PlanStatusEnum> planStatusEnumList=new ArrayList<PlanStatusEnum>();
		for(PlanStatusEnum item:PlanStatusEnum.values()){
			planStatusEnumList.add(item);
		}
		map.put("planStatusEnumList",planStatusEnumList);
		return "plan/" + path;
	}
}
