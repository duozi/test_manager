package com.xn.manage.autotestController;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xn.common.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestServiceService;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.AppendParamEnum;
import com.xn.manage.Enum.ParamTypeEnum;
import com.xn.manage.Enum.RedisAssertTypeEnum;
import com.xn.manage.Enum.RedisOperationTypeEnum;

@Controller
@RequestMapping("/autotest/case")
public class CaseController {

//	@Resource
//	TestSystemService testSystemService;
//
//	@Resource
//	TestServiceService testServiceService;

	@Resource
	CompanyService companyService;

	@RequestMapping(value="/{path}", method = RequestMethod.GET)
	public String getCasePage(@PathVariable String  path, HttpServletRequest request, ModelMap map) {
		String type=request.getParameter("caseType");
		companyService.toString();
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

//		List<System> systemList = new ArrayList<System>();
//
//
//		List<Service> serviceList = new ArrayList<Service>();
//
//
//		map.put("serviceList", serviceList);
//		map.put("systemList",systemList);
		map.put("dbNameList",dbNameList);
		map.put("redisNameList",redisNameList);
		map.put("redisAssertTypeEnumList",redisAssertTypeEnumList);
		map.put("redisOperationTypeEnumList",redisOperationTypeEnumList);
		map.put("appendParamList",appendParamEnumList);
		return "/autotest/case/" + path;
	}

}
