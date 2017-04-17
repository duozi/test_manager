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
import com.xn.manage.Enum.PublishEnum;
import com.xn.performance.dto.PerformanceScenarioDto;
import com.xn.performance.service.PerformanceScenarioService;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.DateUtil;
import com.xn.performance.util.ValidateUtil;
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

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/scenario")
public class PerformanceScenarioController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
    @Resource
    PerformanceScenarioService performanceScenarioService;
    @Resource
    CompanyService companyService;

    @Resource
    DepartmentService departmentService;

    @Resource
    TestSystemService testSystemService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {
        List<PerformanceScenarioDto> performanceScenarioDtoList = null;
        PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
        performanceScenarioDtoList = performanceScenarioService.list(performanceScenarioDto);
        model.put("scenario_list_all", performanceScenarioDtoList);
        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String psystem = request.getParameter("psystem");
        String scenarioName = request.getParameter("scenarioName");
        String scenarioStatus = request.getParameter("scenarioStatus");

        if (isNotEmpty(company) && !company.equals("null")) {
            performanceScenarioDto.setCompany(company);
        }
        if (isNotEmpty(department) && !department.equals("null")) {
            performanceScenarioDto.setDepartment(department);
        }
        if (isNotEmpty(psystem) && !psystem.equals("null")) {
            performanceScenarioDto.setPsystem(psystem);
        }
        if (isNotEmpty(scenarioName) && !scenarioName.equals("null")) {
            performanceScenarioDto.setScenarioName(scenarioName);
        }
        if (isNotEmpty(scenarioStatus) && !scenarioStatus.equals("null")) {
            performanceScenarioDto.setScenarioStatus(scenarioStatus);
        }
        performanceScenarioDtoList = performanceScenarioService.list(performanceScenarioDto);
        model.put("scenario_list", performanceScenarioDtoList);
        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = testSystemService.list(new TestSystemDto());
        model.put("companyList", companyDtoList);
        model.put("departmentList", departmentDtoList);
        model.put("psystemList", testSystemDtoList);
        List<PublishEnum> publishEnumList = new ArrayList<PublishEnum>();
        for (PublishEnum item : PublishEnum.values()) {
            publishEnumList.add(item);
        }
        model.put("publish_list", publishEnumList);

        return "/performance/scenario/" + path;
    }

    @RequestMapping(value = "/scenario_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveScenario(PerformanceScenarioDto performanceScenarioDto,@RequestParam String startTime,@RequestParam String endTime) {
        CommonResult commonResult = new CommonResult();
        try {
            performanceScenarioDto.setScenarioStatus(PublishEnum.UNPUBLISHED.getName());
            if (!ValidateUtil.validate(performanceScenarioDto)) {
                logger.warn(String.format("参数有误", performanceScenarioDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }
            Date setStartTime = DateUtil.getStandardStringDate(startTime);
            Date setEndTime = DateUtil.getStandardStringDate(endTime);
            performanceScenarioDto.setSetStartTime(setStartTime);
            performanceScenarioDto.setSetEndTime(setEndTime);
            if(setStartTime!=null||setEndTime!=null||performanceScenarioDto.getDelayTime()!=null||performanceScenarioDto.getExecuteTime()!=null){
                performanceScenarioDto.setScheduler("true");
            }else{
                performanceScenarioDto.setScheduler("false");
            }

            performanceScenarioService.save(performanceScenarioDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }


    @RequestMapping(value = "/scenario_list/edit", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult editScenario(PerformanceScenarioDto performanceScenarioDto) {
        CommonResult commonResult = new CommonResult();
        try {
            if (!ValidateUtil.validate(performanceScenarioDto)) {
                logger.warn(String.format("参数有误", performanceScenarioDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

            performanceScenarioService.update(performanceScenarioDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }

    @RequestMapping(value = "/scenario_list/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteScenario(@RequestParam Integer id) {
        CommonResult commonResult = new CommonResult();
        try {

            PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
            performanceScenarioDto.setId(id);
            int n = performanceScenarioService.delete(performanceScenarioDto);
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
        } finally {
            return commonResult;
        }
    }

}

