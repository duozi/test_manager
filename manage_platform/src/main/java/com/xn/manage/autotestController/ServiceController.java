package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.container.page.Page;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.manage.utils.ModelUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.api.TestServiceService;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestServiceDto;
import com.xn.interfacetest.dto.TestSystemDto;

@Controller
@RequestMapping("/autotest/service")
public class ServiceController {
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TestServiceService testServiceService;

	@Autowired
	private TestSystemService testSystemService;


	@RequestMapping(value="/service_list", method = RequestMethod.GET)
	public String getSystemPage(HttpServletRequest request, ModelMap model, PageInfo pageInfo) {
		StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数

		//查询公司
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto dto = new CompanyDto();
		companyList = companyService.list(dto);

		//查询系统
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = testSystemService.list(systemDto);

		//查询部门
		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
		DepartmentDto departmentDto = new DepartmentDto();
		departmentList = departmentService.list(departmentDto);

		//查询服务-获取参数
		String companyId = request.getParameter("selectCompanyId");
		String departmentId = request.getParameter("selectDepartmentId");
		String systemId = request.getParameter("selectSystemId");
		String serviceName = request.getParameter("serviceName");

		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(companyId) && !"null".equals(companyId)){
			params.put("companyId",companyId);
			model.put("companyId",companyId);
			pageParams.append("&selectCompanyId=").append(companyId);
		}

		if(StringUtils.isNotBlank(departmentId) && !"null".equals(departmentId)){
			params.put("departmentId",departmentId);
			model.put("departmentId",departmentId);
			pageParams.append("&selectDepartmentId=").append(departmentId);
		}

		if(StringUtils.isNotBlank(systemId) && !"null".equals(systemId)){
			params.put("systemId",systemId);
			model.put("systemId",systemId);
			pageParams.append("&selectSystemId=").append(systemId);
		}

		if(StringUtils.isNotBlank(serviceName) && !"null".equals(serviceName)){
			params.put("name",serviceName);
			model.put("name",serviceName);
			pageParams.append("&serviceName=").append(serviceName);
		}


		if (pageInfo.getCurrentPage() < 1) {
			pageInfo.setCurrentPage(1);
		}
		pageInfo.setPagination(true);
		pageInfo.setPageSize(10);
		params.put("page", pageInfo);
		pageInfo.setParams(pageParams.toString());

		PageResult<TestServiceDto> serviceList = testServiceService.listByParams(params);
		ModelUtils.setResult(model, serviceList.getPage(), serviceList.getList());
		model.put("systemList", systemList);
		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "autotest/service/service_list";
	}


	@RequestMapping(value="/saveService", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveService(TestServiceDto testServiceDto) {
		CommonResult result = new CommonResult();
		try{
			if(StringUtils.isNotBlank(testServiceDto.getName()) && !"null".equals(testServiceDto.getName())){
				//判断名称是否重复
				TestServiceDto exist = testServiceService.getByName(testServiceDto.getName());
				if(null != exist && testServiceDto.getId() != exist.getId()){
					result.setCode(CommonResultEnum.ERROR.getReturnCode());
					result.setMessage("服务名称已存在");
					return  result;
				}
				testServiceService.save(testServiceDto);
			} else {
				result.setCode(CommonResultEnum.ERROR.getReturnCode());
				result.setMessage("name不能为空！");
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存公司异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteService", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteService(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testServiceService.deleteByPK(Long.parseLong(id));
			}
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message = e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("删除操作异常｛｝",e);
		}
		return  result;
	}


}
