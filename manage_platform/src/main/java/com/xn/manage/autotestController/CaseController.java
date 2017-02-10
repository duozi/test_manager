package com.xn.manage.autotestController;

import com.xn.manage.Enum.AppendParamEnum;
import com.xn.manage.Enum.ParamTypeEnum;
import com.xn.manage.Enum.RedisAssertTypeEnum;
import com.xn.manage.Enum.RedisOperationTypeEnum;
import com.xn.manage.bean.Service;
import com.xn.manage.bean.System;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/case")
public class CaseController {
	
	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path, HttpServletRequest request, ModelMap map) {
		String type=request.getParameter("caseType");

		map.put("caseType",type);

		List<ParamTypeEnum> paramTypeList = new ArrayList<ParamTypeEnum>();

		for(ParamTypeEnum item : ParamTypeEnum.values()){
			paramTypeList.add(item);
		}

		List<RedisAssertTypeEnum> redisAssertTypeEnumList=new ArrayList<RedisAssertTypeEnum>();
		for(RedisAssertTypeEnum item:RedisAssertTypeEnum.values()){
			redisAssertTypeEnumList.add(item);
		}
		List<RedisOperationTypeEnum> redisOperationTypeEnumList=new ArrayList<RedisOperationTypeEnum>();
		for(RedisOperationTypeEnum item:RedisOperationTypeEnum.values()){
			redisOperationTypeEnumList.add(item);
		}
		List<AppendParamEnum> appendParamEnumList=new ArrayList<AppendParamEnum>();
		for(AppendParamEnum item:AppendParamEnum.values()){
			appendParamEnumList.add(item);
		}
		List<String> dbNameList=new ArrayList<String>();
		List<String> redisNameList=new ArrayList<String>();
		map.put("paramTypeList",paramTypeList);

		List<System> systemList = new ArrayList<System>();
		systemList.add(new System(1,"风控规则"));
		systemList.add(new System(2,"支付中心"));
		systemList.add(new System(3,"征信公司"));
		systemList.add(new System(4,"商户平台"));

		List<Service> serviceList = new ArrayList<Service>();
		serviceList.add(new Service(1,"ruleengineService"));
		serviceList.add(new Service(2,"riskWeb"));
		serviceList.add(new Service(3,"huaZhengCreditService"));

		map.put("serviceList", serviceList);
		map.put("systemList",systemList);
		map.put("dbNameList",dbNameList);
		map.put("redisNameList",redisNameList);
		map.put("redisAssertTypeEnumList",redisAssertTypeEnumList);
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("appendParamList",appendParamEnumList);
		return "/autotest/case/" + path;
	}

}
