package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.CompanyDto;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.api.TestSystemService;

@Controller
@RequestMapping("/autotest/manage")
public class ManageController {
	private static final Logger logger = LoggerFactory.getLogger(ManageController.class);


	@Resource
	private CompanyService companyService;

	@Resource
	private TestSystemService testSystemService;

	@Resource
	private DepartmentService departmentService;

	@RequestMapping(value="/company_manage", method = RequestMethod.GET)
	public String getCompanyPage(ModelMap model) {
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto dto = new CompanyDto();
		companyList = companyService.list(dto);

//		List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
//		DepartmentDto departmentDto = new DepartmentDto();
//		departmentList = departmentService.list(departmentDto);
//
//		model.put("departmentList", departmentList);
		model.put("companyList", companyList);
		return "/autotest/manage/company_manage";
	}

	@RequestMapping(value="/saveCompany", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult saveCompany(CompanyDto companyDto) {
		CommonResult result = new CommonResult();
		try{
			companyService.save(companyDto);
		}catch (Exception e){
			int code = CommonResultEnum.ERROR.getReturnCode();
			String message =e.getMessage();
			result.setCode(code);
			result.setMessage(message);
			logger.error("保存公司异常｛｝",e);
		}
		return  result;
	}

	@RequestMapping(value="/deleteCompany", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult deleteCompanyBatch(@RequestParam String ids) {
		CommonResult result = new CommonResult();
		List<Long> idList = new ArrayList<Long>();
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String idStr:idArray) {
				if (StringUtils.isNotBlank(idStr)){
					idList.add(Long.parseLong(idStr));
				}
			}
		}
		try{
			companyService.deleteBatchByPK(idList);
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
