package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.google.common.collect.Lists;
import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.common.utils.DateUtil;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.Enum.PerformancePlanStatusEnum;
import com.xn.performance.api.*;
import com.xn.performance.dto.*;
import com.xn.performance.mybatis.CommonResult;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/plan")
public class PerformancePlanController {
    private static final Logger logger = LoggerFactory.getLogger(PerformancePlanController.class);
    @Resource
    PerformanceScriptService performanceScriptService;
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);
    @Resource
    private CompanyService companyService;
    @Resource
    private TestSystemService testSystemService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private PerformancePlanService performancePlanService;

    @Resource
    private PerformanceScenarioService performanceScenarioService;


    @Resource
    private JmeterService jmeterService;
    @Resource
    private PerformanceResultService performanceResultService;
    @Resource
    private PerformanceMonitoredMachineService performanceMonitoredMachineService;

    @Resource
    private PerformancePlanMonitoredService performancePlanMonitoredService;

    @Resource
    private PerformanceStressMachineService performanceStressMachineService;
    @Resource
    private PerformanceMonitoredMachineResultService performanceMonitoredMachineResultService;
    @Resource
    private PerformancePlanShowService performancePlanShowService;

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
        PerformancePlanShowDto performancePlanShowDto = new PerformancePlanShowDto();
        performancePlanShowDto.setIsDelete("未删除");
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

        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanShowService.getPlanShow(performancePlanShowDto);
        model.put("plan_list", performancePlanShowDtoList);

        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = testSystemService.list(new TestSystemDto());
        model.put("companyList", companyDtoList);
        model.put("departmentList", departmentDtoList);
        model.put("psystemList", testSystemDtoList);
        List<PerformancePlanStatusEnum> performancePlanStatusEnumList = new ArrayList<>();
        for (PerformancePlanStatusEnum item : PerformancePlanStatusEnum.values()) {
            performancePlanStatusEnumList.add(item);
        }
        model.put("plan_status_list", performancePlanStatusEnumList);

        return "/performance/plan/" + path;
    }


    //保存计划
    @RequestMapping(value = "/plan_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult savePlan(PerformancePlanDto performancePlanDto, @RequestParam String list) {

        CommonResult commonResult = new CommonResult();
        try {

            JSONArray jsonArray = JSONArray.fromObject(list);
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = (List) JSONArray.toCollection(jsonArray, PerformancePlanMonitoredDto.class);


            performancePlanDto.setPlanStatus(PerformancePlanStatusEnum.UNEXECUTED.getName());
            performancePlanDto.setIsDelete("未删除");


            performancePlanDto = performancePlanService.save(performancePlanDto);

            for (PerformancePlanMonitoredDto performancePlanMonitoredDto : performancePlanMonitoredDtoList) {
                Integer id = performancePlanMonitoredDto.getMonitoredMachineId();
                PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();
                performanceMonitoredMachineDto.setId(id);
                performanceMonitoredMachineDto = performanceMonitoredMachineService.get(performanceMonitoredMachineDto);
                performancePlanMonitoredDto.setPlanId(performancePlanDto.getId());

                performancePlanMonitoredDto.setMonitoredMachineIp(performanceMonitoredMachineDto.getIp());

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
    public CommonResult executeSave(@RequestParam Integer planId, @RequestParam Integer stressMachineId, @RequestParam String stressMachineName, @RequestParam final String executeType, @RequestParam String setStartTime, @RequestParam String executePerson) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceResultDto performanceResultDto = new PerformanceResultDto();

            performanceResultDto.setPlanId(planId);
            performanceResultDto.setStressMachineId(stressMachineId);
            performanceResultDto.setStressMachineName(stressMachineName);
            performanceResultDto.setExecutePerson(executePerson);
            performanceResultDto.setExecuteStatus("等待中");

            if (executeType.equals("schedule")) {
                Date time = DateUtil.getStandardStringDate(setStartTime);
                performanceResultDto.setSetStartTime(time);
            }
            //先保存到执行结果
            performanceResultDto = performanceResultService.save(performanceResultDto);
            //保存到plan_monitored_result表
            Integer resultId = performanceResultDto.getId();
            PerformancePlanMonitoredDto performancePlanMonitoredDto = new PerformancePlanMonitoredDto();
            performancePlanMonitoredDto.setPlanId(planId);
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = performancePlanMonitoredService.list(performancePlanMonitoredDto);
            for (PerformancePlanMonitoredDto item : performancePlanMonitoredDtoList) {
                Integer monitoredMachineId = item.getMonitoredMachineId();
                String monitoredMachineName = item.getMonitoredMachineName();
                PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();
                performanceMonitoredMachineDto.setId(monitoredMachineId);
                performanceMonitoredMachineDto = performanceMonitoredMachineService.get(performanceMonitoredMachineDto);

                String monitoredMachineIP = performanceMonitoredMachineDto.getIp();
                PerformanceMonitoredMachineResultDto performanceMonitoredResultDto = new PerformanceMonitoredMachineResultDto();
                performanceMonitoredResultDto.setPlanId(planId);
                performanceMonitoredResultDto.setMonitoredMachineId(monitoredMachineId);
                performanceMonitoredResultDto.setMonitoredMachineName(monitoredMachineName);
                performanceMonitoredResultDto.setMonitoredMachineIp(monitoredMachineIP);
                performanceMonitoredResultDto.setResultId(resultId);
                performanceMonitoredMachineResultService.save(performanceMonitoredResultDto);
            }

            final PerformanceResultDto finalPerformanceResultDto = performanceResultDto;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("============" + Thread.currentThread().getName());
                    jmeterService.executePlan(executeType, finalPerformanceResultDto);
                }
            });


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

    //停止执行
    @RequestMapping(value = "/plan_list/stop", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult stopPlan(@RequestParam final Integer id, @RequestParam final Integer planId, @RequestParam final String executeStatus) {
        CommonResult commonResult = new CommonResult();
        try {

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("stopPlan============" + Thread.currentThread().getName());
                    jmeterService.stopPlan(id, planId, executeStatus);
                }
            });


        } catch (Exception e) {

            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("停止执行操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }


}

