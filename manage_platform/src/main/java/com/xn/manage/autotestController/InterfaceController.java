package com.xn.manage.autotestController;

import com.xn.manage.Enum.*;
import com.xn.manage.bean.Service;
import com.xn.manage.bean.System;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autotest/interface")
public class InterfaceController {


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
		List<RedisOperationTypeEnum> redisOperationTypeEnumList=new ArrayList<RedisOperationTypeEnum>();
		for(RedisOperationTypeEnum item:RedisOperationTypeEnum.values()){
			redisOperationTypeEnumList.add(item);
		}

		List<String> dbNameList=new ArrayList<String>();
		List<String> redisNameList=new ArrayList<String>();

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
		map.put("requestTypeList",requestTypeList);
		map.put("contentTypeList",contentTypeList);
		map.put("httpTypeList",httpTypeList);
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("dbNameList",dbNameList);
		map.put("redisNameList",redisNameList);
		return "/autotest/interface/" + path;
	}

}
