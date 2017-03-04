package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */


import com.google.common.collect.Lists;
import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.PlanStatusEnum;
import com.xn.performance.dto.*;
import com.xn.performance.service.*;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.ValidateUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private PerformanceMonitoredMachineService performanceMonitoredMachineService;

    @Autowired
    private PerformancePlanMonitoredService performancePlanMonitoredService;
    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {

        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanService.show(performancePlanDto);
        model.put("plan_list_all", performancePlanShowDtoList);

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
//        performancePlanDtoList = performancePlanService.list(performancePlanDto);
//        model.put("plan_list", performancePlanDtoList);

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
//

    @RequestMapping(value = "/plan_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult savePlan(PerformancePlanDto performancePlanDto, @RequestParam String list) {

        CommonResult commonResult = new CommonResult();
        try {

            JSONArray jsonArray=JSONArray.fromObject(list);
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = (List) JSONArray.toCollection(jsonArray, PerformancePlanMonitoredDto.class);


            performancePlanDto.setPlanStatus(PlanStatusEnum.UN_EXECUTE.getName());

            if (!ValidateUtil.validate(performancePlanDto)) {
                logger.warn(String.format("参数有误", performancePlanDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

             performancePlanDto=performancePlanService.save(performancePlanDto);

            for(PerformancePlanMonitoredDto performancePlanMonitoredDto:performancePlanMonitoredDtoList){
                performancePlanMonitoredDto.setPlanId(performancePlanDto.getId());
            }

            performancePlanMonitoredService.save(Lists.newArrayList(performancePlanMonitoredDtoList));


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
    public CommonResult getScriptAndScenarioAndMonitored(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
            PerformanceScenarioDto performanceScenarioDto=new PerformanceScenarioDto();
            PerformanceMonitoredMachineDto performanceMonitoredMachineDto=new PerformanceMonitoredMachineDto();

            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            if (isNotEmpty(company) && !company.equals("null")) {
                performanceScriptDto.setCompany(company);
                performanceScenarioDto.setCompany(company);
                performanceMonitoredMachineDto.setCompany(company);
            }
            if (isNotEmpty(department) && !department.equals("null")) {
                performanceScriptDto.setDepartment(department);
                performanceScenarioDto.setDepartment(department);
                performanceMonitoredMachineDto.setDepartment(department);
            }
            if (isNotEmpty(psystem) && !psystem.equals("null")) {
                performanceScriptDto.setPsystem(psystem);
                performanceScenarioDto.setPsystem(psystem);
                performanceMonitoredMachineDto.setPsystem(psystem);
            }
            List<PerformanceScriptDto> performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
            List<PerformanceScenarioDto> performanceScenarioDtoList = performanceScenarioService.list(performanceScenarioDto);
            List<PerformanceMonitoredMachineDto> performanceMonitoredMachineDtoList = performanceMonitoredMachineService.list(performanceMonitoredMachineDto);
            List list=new ArrayList();
            list.add(performanceScriptDtoList);
            list.add(performanceScenarioDtoList);
            list.add(performanceMonitoredMachineDtoList);
           commonResult.setData(list);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("查询操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }
}

