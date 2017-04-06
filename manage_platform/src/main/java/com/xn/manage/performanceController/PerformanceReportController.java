package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.common.utils.DateUtil;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.ExecuteStatusEnum;
import com.xn.performance.api.*;
import com.xn.performance.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/report")
public class PerformanceReportController {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceReportController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TestSystemService systemService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    PerformanceResultService performanceResultService;
    @Autowired
    PerformancePlanMonitoredService performancePlanMonitoredService;
    @Autowired
    PerformancePlanService performancePlanService;
    @Autowired
    PerformanceStressMachineService performanceStressMachineService;
    @Autowired
    PerformanceScriptService performanceScriptService;
    @Autowired
    PerformanceScenarioService performanceScenarioService;
    @Autowired
    PerformanceMonitoredMachineResultService performanceMonitoredMachineResultService;

    @Autowired
    PerformancePlanShowService performancePlanShowService;


    @RequestMapping(value="/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {

        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        List<PerformancePlanDto> performancePlanDtoList = performancePlanService.list(performancePlanDto);
        model.put("plan_list_all", performancePlanDtoList);


        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        List<PerformanceScriptDto> performanceScriptDtoList  = performanceScriptService.list(performanceScriptDto);
        model.put("script_list_all", performanceScriptDtoList);


        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String psystem = request.getParameter("psystem");
        String planName = request.getParameter("planName");
        String planStatus = request.getParameter("planStatus");
        String scriptName = request.getParameter("scriptName");
        String actualStartTimeBegin=request.getParameter("actualStartTimeBegin");
        String actualStartTimeEnd=request.getParameter("actualStartTimeEnd");
        PerformancePlanShowDto performancePlanShowDto=new PerformancePlanShowDto();
        if (isNotEmpty(company) && !company.equals("null")) {
            performancePlanShowDto.setCompany(company);
        }
        if (isNotEmpty(department) && !department.equals("null")) {
            performancePlanShowDto.setDepartment(department);
        }
        if (isNotEmpty(psystem) && !psystem.equals("null")) {
            performancePlanShowDto.setPsystem(psystem);
        }
        if (isNotEmpty(planName) && !planName.equals("null")) {
            performancePlanShowDto.setPlanName(planName);
        }
        if (isNotEmpty(planStatus) && !planStatus.equals("null")) {
            performancePlanShowDto.setPlanStatus(planStatus);
        }
        if (isNotEmpty(scriptName) && !scriptName.equals("null")) {
            performancePlanShowDto.setScriptName(scriptName);
        }
        if (isNotEmpty(actualStartTimeBegin) && !actualStartTimeBegin.equals("null")) {
            Date begin= DateUtil.getStandardStringDate(actualStartTimeBegin);
            performancePlanShowDto.setActualStartTimeBegin(begin);
        }
        if (isNotEmpty(actualStartTimeEnd) && !actualStartTimeEnd.equals("null")) {
            Date end= DateUtil.getStandardStringDate(actualStartTimeEnd);
            performancePlanShowDto.setActualStartTimeEnd(end);
        }

        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanShowService.getResultList(performancePlanShowDto);
        model.put("result_list", performancePlanShowDtoList);

        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = systemService.list(new TestSystemDto());
        model.put("companyList", companyDtoList);
        model.put("departmentList", departmentDtoList);
        model.put("psystemList", testSystemDtoList);
        List<ExecuteStatusEnum> executeStatusEnumList = new ArrayList<>();
        for (ExecuteStatusEnum item : ExecuteStatusEnum.values()) {
            executeStatusEnumList.add(item);
        }
        model.put("execute_status_list", executeStatusEnumList);

        return "/performance/report/" + path;
    }


    //获得报告
    @RequestMapping(value = "/report_detail", method = RequestMethod.GET)
    public String getReport(HttpServletRequest request, ModelMap model) {
        try {
            //获得结果相关信息
            Integer id = Integer.parseInt(request.getParameter("id"));
            PerformanceResultDto performanceResultDto = new PerformanceResultDto();
            performanceResultDto.setId(id);
            performanceResultDto = performanceResultService.get(performanceResultDto);

            //获得计划相关信息
            Integer planId = performanceResultDto.getPlanId();
            PerformancePlanDto performancePlanDto = new PerformancePlanDto();
            performancePlanDto.setId(planId);
            performancePlanDto = performancePlanService.get(performancePlanDto);
            //监控机
            PerformanceMonitoredMachineResultDto performancePlanMonitoredDto = new PerformanceMonitoredMachineResultDto();
            performancePlanMonitoredDto.setResultId(id);
            List<PerformanceMonitoredMachineResultDto> performanceMonitoredMachineResultDtoList = performanceMonitoredMachineResultService.list(performancePlanMonitoredDto);
            //压力机
            Integer stressMachineId = performanceResultDto.getStressMachineId();
            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
            performanceStressMachineDto.setId(stressMachineId);
            performanceStressMachineDto = performanceStressMachineService.get(performanceStressMachineDto);
            //脚本
            Integer scriptId = performancePlanDto.getScriptId();
            PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
            performanceScriptDto.setId(scriptId);
            performanceScriptDto = performanceScriptService.get(performanceScriptDto);
            //场景
            Integer scenarioId = performancePlanDto.getScenarioId();
            PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
            performanceScenarioDto.setId(scenarioId);
            performanceScenarioDto = performanceScenarioService.get(performanceScenarioDto);


            //暂时修改对应报告的地址
            String resultPath = performanceResultDto.getResultPath();
            int i = resultPath.lastIndexOf("/");
            if ((i > -1) && (i < (resultPath.length()))) {
                resultPath = resultPath.substring(i);
                performanceResultDto.setResultPath(resultPath);
            }

            model.put("result_detail", performanceResultDto);
            model.put("plan_detail", performancePlanDto);
            model.put("monitored_machine_detail_list", performanceMonitoredMachineResultDtoList);
            model.put("stress_machine_detail", performanceStressMachineDto);
            model.put("script_detail", performanceScriptDto);
            model.put("scenario_detail", performanceScenarioDto);


        } catch (Exception e) {

            logger.error("获得报告操作异常｛｝", e);
        } finally {
            return "/performance/report/report_detail";
        }
    }
}

