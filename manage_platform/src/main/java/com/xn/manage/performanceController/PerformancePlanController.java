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
import com.xn.performance.util.DateUtil;
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

import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/plan")
public class PerformancePlanController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
    @Resource
    PerformanceScriptService performanceScriptService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TestSystemService systemService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PerformancePlanService performancePlanService;

    @Autowired
    private PerformanceScenarioService performanceScenarioService;


    @Autowired
    private JmeterService jmeterService;
    @Autowired
    private  PerformanceResultService performanceResultService;
    @Autowired
    private PerformanceMonitoredMachineService performanceMonitoredMachineService;

    @Autowired
    private PerformancePlanMonitoredService performancePlanMonitoredService;

    @Autowired
    private PerformanceStressMachineService performanceStressMachineService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {

        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setIsDelete("未删除");
        List<PerformancePlanDto> performancePlanDtoList = performancePlanService.list(performancePlanDto);
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

        List<PerformancePlanShowDto> performancePlanShowDtoList = getPlanShow(performancePlanDto);
        model.put("plan_list", performancePlanShowDtoList);

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


    private List<PerformancePlanShowDto> getPlanShow(PerformancePlanDto performancePlanDto) {
        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanService.show(performancePlanDto);
        for (PerformancePlanShowDto performancePlanShowDto : performancePlanShowDtoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("planId", performancePlanShowDto.getId());
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = performancePlanMonitoredService.list(map);
            performancePlanShowDto.setPerformancePlanMonitoredDtoList(performancePlanMonitoredDtoList);
        }
        return performancePlanShowDtoList;
    }

    //保存计划
    @RequestMapping(value = "/plan_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult savePlan(PerformancePlanDto performancePlanDto, @RequestParam String list) {

        CommonResult commonResult = new CommonResult();
        try {

            JSONArray jsonArray = JSONArray.fromObject(list);
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = (List) JSONArray.toCollection(jsonArray, PerformancePlanMonitoredDto.class);


            performancePlanDto.setPlanStatus(PlanStatusEnum.UN_EXECUTE.getName());
            performancePlanDto.setIsDelete("未删除");

            if (!ValidateUtil.validate(performancePlanDto)) {
                logger.warn(String.format("参数有误", performancePlanDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

            performancePlanDto = performancePlanService.save(performancePlanDto);

            for (PerformancePlanMonitoredDto performancePlanMonitoredDto : performancePlanMonitoredDtoList) {
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

    //    逻辑删除一个计划
    @RequestMapping(value = "/plan_list/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deletePlan(@RequestParam Integer id) {
        CommonResult commonResult = new CommonResult();
        try {

            PerformancePlanDto performancePlanDto = new PerformancePlanDto();
            performancePlanDto.setId(id);
            performancePlanDto.setIsDelete("已删除");
            int n = performancePlanService.update(performancePlanDto);
            if (n == 0) {
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
            } else {
                commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
                commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
            }

        } catch (Exception e) {

            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("删除操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }

    //执行计划，根据已知的公司，部门，系统展示可选的压力机
    @RequestMapping(value = "/plan_list/show_stress_machine", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult showStressMachine(@RequestParam String company, @RequestParam String department, @RequestParam String psystem) {
        CommonResult commonResult = new CommonResult();
        try {

            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
            performanceStressMachineDto.setCompany(company);
            performanceStressMachineDto.setDepartment(department);
            performanceStressMachineDto.setPsystem(psystem);
            List<PerformanceStressMachineDto> performanceStressMachineDtoList = performanceStressMachineService.list(performanceStressMachineDto);

            commonResult.setData(performanceStressMachineDtoList);
        } catch (Exception e) {

            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("获得压力机操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }

    //保存执行配置
    @RequestMapping(value = "/plan_list/execute_save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult executeSave(@RequestParam Integer planId, @RequestParam Integer stressMachineId, @RequestParam Integer stressMachineName, @RequestParam String executeType, @RequestParam String setStartTime,@RequestParam String executePerson) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceResultDto performanceResultDto = new PerformanceResultDto();
            performanceResultDto.setPlanId(planId);
            performanceResultDto.setStressMachineId(stressMachineId);
            performanceResultDto.setStressMachineName(stressMachineName);
            performanceResultDto.setExecutePerson(executePerson);
            performanceResultDto.setExecuteStatus(PlanStatusEnum.UN_EXECUTE.getName());

            if (executeType.equals("schedule")) {
                Date time = DateUtil.getStandardStringDate(setStartTime);
                performanceResultDto.setSetStartTime(time);
            }else if(executeType.equals("now")){
                Date time = DateUtil.getStandardStringDate(setStartTime);
                performanceResultDto.setSetStartTime(time);
            }
            //先保存到执行结果
           PerformanceResultDto performanceResultDto1= performanceResultService.save(performanceResultDto);
            jmeterService.executePlan(executeType,performanceResultDto1);

        } catch (Exception e) {

            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("保存执行配置操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }

    //    新增计划，根据公司，部门，系统，展示可选的脚本，场景和监控机
    @RequestMapping(value = "/plan_list/show_script", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getScriptAndScenarioAndMonitored(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
            PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
            PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();

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
            List list = new ArrayList();
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

