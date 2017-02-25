package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.entity.TestSystem;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.performance.util.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/autotest/manage")
public class SystemController {
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	@Resource
	private CompanyService companyService;

	@Resource
	private TestSystemService testSystemService;

	@Resource
	private DepartmentService departmentService;

	@RequestMapping(value="/system_list", method = RequestMethod.GET)
	public String getSystem(HttpServletRequest request, ModelMap model) {
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto dto = new CompanyDto();
		companyList = companyService.list(dto);

		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
		DepartmentDto departmentDto = new DepartmentDto();
		departmentList = departmentService.list(departmentDto);

		String companyId = request.getParameter("selectCompanyId");
		String departmentId = request.getParameter("selectDepartmentId");
		String systenmName = request.getParameter("systenmName");

		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(companyId) && !"null".equals(companyId)){
			params.put("companyId",companyId);
			model.put("companyId",companyId);
		}

		if(StringUtils.isNotBlank(departmentId) && !"null".equals(departmentId)){
			params.put("departmentId",departmentId);
			model.put("departmentId",departmentId);
		}

		if(StringUtils.isNotBlank(systenmName) && !"null".equals(systenmName)){
			params.put("name",systenmName);
			model.put("name",systenmName);
		}

		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		systemList = testSystemService.listByCompany(params);

		model.put("systemList", systemList);
		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "/autotest/manage/system_list";
	}

	@RequestMapping(value="/getSystem")
	@ResponseBody
	public List<TestSystemDto> getSystemPage(String id) {
		List<TestSystemDto> systemList = new ArrayList<TestSystemDto>();
		TestSystemDto systemDto = new TestSystemDto();
		systemList = testSystemService.list(systemDto);
		return systemList;
	}


	@RequestMapping(value="/saveSystem", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveSystem(TestSystemDto systemDto) {
		CommonResult result = new CommonResult();
		try{
			testSystemService.save(systemDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存公司异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteSystem", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteSystem(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		String id = request.getParameter("id");
		try{
			if(StringUtils.isNotBlank(id)){
				testSystemService.deleteByPK(Long.parseLong(id));
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
