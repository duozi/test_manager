package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.performance.dto.*;
import com.xn.performance.service.*;
import com.xn.performance.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/performance/report")
public class PerformanceReportController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
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
//
//    @RequestMapping(value="/{path}", method = RequestMethod.GET)
//    public String common(@PathVariable String  path, ModelMap model) {
//        System.out.println("============");
//        return "/performance/report/" + path;
//    }

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
            PerformanceMonitoredMachineResultDto performancePlanMonitoredDto=new PerformanceMonitoredMachineResultDto();
            performancePlanMonitoredDto.setResultId(id);
            List<PerformanceMonitoredMachineResultDto> performanceMonitoredMachineResultDtoList=performanceMonitoredMachineResultService.list(performancePlanMonitoredDto);
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



            model.put("result_detail", performanceResultDto);
            model.put("plan_detail", performancePlanDto);
            model.put("monitored_machine_detail_list", performanceMonitoredMachineResultDtoList);
            model.put("stress_machine_detail", performanceStressMachineDto);
            model.put("script_detail",performanceScriptDto);
            model.put("scenario_detail",performanceScenarioDto);




        } catch (Exception e) {

            logger.error("获得报告操作异常｛｝", e);
        } finally {
            return "/performance/report/report_detail";
        }
    }
}

