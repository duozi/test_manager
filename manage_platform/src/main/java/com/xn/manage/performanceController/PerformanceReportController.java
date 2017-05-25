package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.common.utils.DateUtil;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.manage.Enum.ExecuteStatusEnum;
import com.xn.performance.api.*;
import com.xn.performance.dto.*;
import com.xn.performance.mybatis.CommonResult;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.xn.common.utils.PropertyUtil.getProperty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/report")
public class PerformanceReportController {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceReportController.class);
    public ExecutorService threadPool = Executors.newFixedThreadPool(5);
    @Resource
    PerformanceResultService performanceResultService;
    @Resource
    PerformancePlanMonitoredService performancePlanMonitoredService;
    @Resource
    PerformancePlanService performancePlanService;
    @Resource
    PerformanceStressMachineService performanceStressMachineService;
    @Resource
    PerformanceScriptService performanceScriptService;
    @Resource
    PerformanceScenarioService performanceScenarioService;
    @Resource
    PerformanceMonitoredMachineResultService performanceMonitoredMachineResultService;
    @Resource
    PerformancePlanShowService performancePlanShowService;
    @Resource
    PerformanceReportService performanceReportService;
    @Resource
    private CompanyService companyService;
    @Resource
    private TestSystemService testSystemService;
    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request, PageInfo pageInfo) {

        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        List<PerformancePlanDto> performancePlanDtoList = performancePlanService.list(performancePlanDto);
        model.put("plan_list_all", performancePlanDtoList);



        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        List<PerformanceScriptDto> performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
        model.put("script_list_all", performanceScriptDtoList);


        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String psystem = request.getParameter("psystem");
        String planName = request.getParameter("planName");
        String executeStatus = request.getParameter("executeStatus");
        String scriptName = request.getParameter("scriptName");
        String actualStartTimeBegin = request.getParameter("actualStartTimeBegin");
        String actualStartTimeEnd = request.getParameter("actualStartTimeEnd");
        PerformancePlanShowDto performancePlanShowDto = new PerformancePlanShowDto();
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
        if (isNotEmpty(executeStatus) && !executeStatus.equals("null")) {
            performancePlanShowDto.setExecuteStatus(executeStatus);
        }
        if (isNotEmpty(scriptName) && !scriptName.equals("null")) {
            performancePlanShowDto.setScriptName(scriptName);
        }
        if (isNotEmpty(actualStartTimeBegin) && !actualStartTimeBegin.equals("null")) {
            Date begin = DateUtil.getStandardStringDate(actualStartTimeBegin);
            performancePlanShowDto.setActualStartTimeBegin(begin);
        }
        if (isNotEmpty(actualStartTimeEnd) && !actualStartTimeEnd.equals("null")) {
            Date end = DateUtil.getStandardStringDate(actualStartTimeEnd);
            performancePlanShowDto.setActualStartTimeEnd(end);
        }
        pageInfo.setPagination(true);
        pageInfo.setPageSize(15);

        PageResult<PerformancePlanShowDto> performancePlanShowDtoList = null;
        try {
            performancePlanShowDtoList = performancePlanShowService.getResultListByPage(performancePlanShowDto, pageInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        model.put("page", performancePlanShowDtoList.getPage());
        model.put("result_list", performancePlanShowDtoList.getList());

        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = testSystemService.list(new TestSystemDto());
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
            if (scenarioId != 0) {
                //如果场景未修改，就不传入场景到后台
                PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
                performanceScenarioDto.setId(scenarioId);
                performanceScenarioDto = performanceScenarioService.get(performanceScenarioDto);
                model.put("scenario_detail", performanceScenarioDto);
            }


            //暂时修改对应报告的地址
            String resultPath = performanceResultDto.getResultPath();
            int i = resultPath.lastIndexOf("/");
            if ((i > -1) && (i < (resultPath.length()))) {
                resultPath = resultPath.substring(i);
                performanceResultDto.setResultPath(resultPath);
            }
            String localIp = getProperty("localIp");
            model.put("result_detail", performanceResultDto);
            model.put("plan_detail", performancePlanDto);
            model.put("monitored_machine_detail_list", performanceMonitoredMachineResultDtoList);
            model.put("stress_machine_detail", performanceStressMachineDto);
            model.put("script_detail", performanceScriptDto);


            model.put("localIp", localIp);


        } catch (Exception e) {

            logger.error("获得报告操作异常｛｝", e);
        } finally {
            return "/performance/report/report_detail";
        }
    }

    //获得机器实时数据
    @RequestMapping(value = "/grafana", method = RequestMethod.GET)
    public String getGrafana(ModelMap model) {
        try {
            String localIp = getProperty("localIp");
            model.put("localIp", localIp);
        } catch (Exception e) {

        } finally {
            return "/performance/report/grafana";
        }
    }
    //获得机器实时数据
    @RequestMapping(value = "/jmeter", method = RequestMethod.GET)
    public String getJmeter(ModelMap model) {
        try {
            String localIp = getProperty("localIp");
            model.put("localIp", localIp);
        } catch (Exception e) {

        } finally {
            return "/performance/report/jmeter";
        }
    }
    //查看jmeter日志
    @RequestMapping(value = "/jmeter_log", method = RequestMethod.GET)
    public String getJmeterLog(HttpServletRequest request, ModelMap model) {
        try {
            //获得结果相关信息
            Integer id = Integer.parseInt(request.getParameter("id"));
            PerformanceResultDto performanceResultDto = new PerformanceResultDto();
            performanceResultDto.setId(id);
            performanceResultDto = performanceResultService.get(performanceResultDto);

            //压力机
            Integer stressMachineId = performanceResultDto.getStressMachineId();
            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
            performanceStressMachineDto.setId(stressMachineId);
            performanceStressMachineDto = performanceStressMachineService.get(performanceStressMachineDto);
            model.put("stress_machine_detail", performanceStressMachineDto);
        } catch (Exception e) {
        } finally {
            return "/performance/report/jmeter_log";
        }
    }

    //    新增计划，根据公司，部门，系统，展示可选的计划
    @RequestMapping(value = "/report_statistic/show_plan", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getPlan(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformancePlanDto performancePlanDto = new PerformancePlanDto();

            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            performancePlanDto.setIsDelete("未删除");
            if (isNotEmpty(company) && !company.equals("null")) {
                performancePlanDto.setCompany(company);
            }
            if (isNotEmpty(department) && !department.equals("null")) {
                performancePlanDto.setDepartment(department);
            }
            if (isNotEmpty(psystem) && !psystem.equals("null")) {
                performancePlanDto.setPsystem(psystem);
            }
            List<PerformancePlanDto> performancePlanDtoList = performancePlanService.list(performancePlanDto);
            commonResult.setData(performancePlanDtoList);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("查询操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }

    //    新增计划，根据公司，部门，系统，展示可选的计划
    @RequestMapping(value = "/report_statistic/show_result", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getResult(HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            PerformanceResultDto performanceResultDto = new PerformanceResultDto();

            String planId = request.getParameter("planId");

            if (isNotEmpty(planId) && !planId.equals("null")) {
                performanceResultDto.setPlanId(Integer.valueOf(planId));
            }

            List<PerformanceResultDto> performanceResultDtoList = performanceResultService.list(performanceResultDto);

            commonResult.setData(performanceResultDtoList);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("查询操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }

    /**
     * {"dbs": ["D:/jmeter-reports/10min-go.db","xxx/xxx.db"]}
     *
     * @return json格式对比数据
     */
    @RequestMapping(value = "/report_statistic/generate_report", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult generateReport(@RequestParam String planStr, @RequestParam String resultStr) {

        CommonResult commonResult = new CommonResult();
        try {
            JSONObject planJson = new JSONObject(planStr);
            JSONObject resultJson = new JSONObject(resultStr);
            org.json.JSONArray jsonArray = resultJson.getJSONArray("resultStr");
            List<Object> report = new ArrayList<>();
            for (int i = 0; i < planJson.getJSONArray("planStr").length(); i++) {

                Map<String, Object> scene = new LinkedHashMap<String, Object>();

                String planIdStr = planJson.getJSONArray("planStr").get(i).toString();
                PerformancePlanDto performancePlanDto=new PerformancePlanDto();
                performancePlanDto.setId(Integer.parseInt(planIdStr));
                performancePlanDto=performancePlanService.get(performancePlanDto);
                String planName=performancePlanDto.getPlanName();
                Integer scenarioId=performancePlanDto.getScenarioId();
                PerformanceScenarioDto performanceScenarioDto=new PerformanceScenarioDto();
                performanceScenarioDto.setId(scenarioId);
                performanceScenarioDto=performanceScenarioService.get(performanceScenarioDto);
                String scenarioName=performanceScenarioDto.getScenarioName();
                Integer concurrency=performanceScenarioDto.getConcurrency();
                scene.put("describe", planName); //计划描述
                scene.put("scenarioName",scenarioName);
                scene.put("concurrency",concurrency);
                org.json.JSONArray resultArray = jsonArray.getJSONArray(i);
                List resultList = resultArray.toList();

                scene.put("db", resultList);
                Map<String, Object> report1 = performanceReportService.generateReport(resultList); //该场景的结果
                scene.putAll(report1);
                report.add(scene);
            }


            commonResult.setData(report);
        } catch (Exception e) {
            commonResult.setMessage(CommonResultEnum.ERROR.getReturnMsg());
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            logger.error("查询操作异常｛｝", e);
        } finally {
            return commonResult;
        }


    }
    /**
     * 返回绘制图表的json数据
     * @param dbfile sqlite数据的路径+文件名称
     * @param jmeterPrefix jmeter性能测试数据表前缀，用于区分不同的tps表，避免数据污染
     * @return json格式的绘制图表数据
     */
    @RequestMapping(value = "/report_statistic/jmter_char" ,method = RequestMethod.POST)
    @ResponseBody
    public JSONObject jmeterChar(@RequestParam Integer id) {
        System.out.println("Spring Controller: from ajax dbfile is:"+id);
        Map<String,Object> map  = JmeterChartSqlite.ChartDataAll(id);
        System.out.println(map);
        return (new JSONObject(map));
    }

}

