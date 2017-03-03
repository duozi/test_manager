package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */


import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.PlanStatusEnum;
import com.xn.performance.dto.PerformancePlanDto;
import com.xn.performance.dto.PerformanceScriptDto;
import com.xn.performance.service.PerformancePlanService;
import com.xn.performance.service.PerformanceScenarioService;
import com.xn.performance.service.PerformanceScriptService;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/plan")
public class PerformancePlanController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
    @Autowired
    private CompanyService companyService;

    @Autowired
    private TestSystemService systemService;

    @Autowired
    private DepartmentService departmentService;

    @Resource
    PerformanceScriptService performanceScriptService;

    @Autowired
    private PerformancePlanService performancePlanService;

    @Autowired
    private PerformanceScenarioService performanceScenarioService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {
        List<PerformancePlanDto> performancePlanDtoList = null;
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDtoList = performancePlanService.list(performancePlanDto);
        model.put("plan_list_all", performancePlanDtoList);

        List<PerformanceScriptDto> performanceScriptDtoList = null;
        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
        model.put("script_list_all", performanceScriptDtoList);


        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String psystem = request.getParameter("psystem");
        String planName = request.getParameter("planName");
        String planStatus = request.getParameter("planStatus");
        String scriptName = request.getParameter("scriptName");

        if (isNotEmpty(company) && !company.equals("null")) {
            performancePlanDto.setCompany(company);
        }
        if (isNotEmpty(department) && !department.equals("null")) {
            performancePlanDto.setDepartment(department);
        }
        if (isNotEmpty(psystem) && !psystem.equals("null")) {
            performancePlanDto.setPsystem(psystem);
        }
        if (isNotEmpty(planName) && !planName.equals("null")) {
            performancePlanDto.setPlanName(planName);
        }
        if (isNotEmpty(planStatus) && !planStatus.equals("null")) {
            performancePlanDto.setPlanStatus(planStatus);
        }
        if (isNotEmpty(scriptName) && !scriptName.equals("null")) {
            performancePlanDto.setPlanStatus(scriptName);
        }
        performancePlanDtoList = performancePlanService.list(performancePlanDto);
        model.put("plan_list", performancePlanDtoList);

        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = systemService.list(new TestSystemDto());
        model.put("companyList", companyDtoList);
        model.put("departmentList", departmentDtoList);
        model.put("psystemList", testSystemDtoList);
        List<PlanStatusEnum> publishEnumList = new ArrayList<>();
        for (PlanStatusEnum item : PlanStatusEnum.values()) {
            publishEnumList.add(item);
        }
        model.put("publish_list", publishEnumList);

        return "/performance/plan/" + path;
    }

    @RequestMapping(value = "/plan_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult savePlan(PerformancePlanDto performancePlanDto) {
        CommonResult commonResult = new CommonResult();
        try {
            performancePlanDto.setPlanStatus(PlanStatusEnum.UNPUBLISHED.getName());

            if (!ValidateUtil.validate(performancePlanDto)) {
                logger.warn(String.format("参数有误", performancePlanDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

            performancePlanService.save(performancePlanDto);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("保存操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }


    @RequestMapping(value = "/plan_list/show_script", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getScript(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            if (isNotEmpty(company) && !company.equals("null")) {
                performanceScriptDto.setCompany(company);
            }
            if (isNotEmpty(department) && !department.equals("null")) {
                performanceScriptDto.setDepartment(department);
            }
            if (isNotEmpty(psystem) && !psystem.equals("null")) {
                performanceScriptDto.setPsystem(psystem);
            }
            List<PerformanceScriptDto> performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
           commonResult.setData(performanceScriptDtoList);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("保存操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }
}

