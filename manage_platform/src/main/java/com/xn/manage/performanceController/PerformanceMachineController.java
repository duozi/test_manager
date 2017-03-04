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
import com.xn.performance.dto.PerformanceMonitoredMachineDto;
import com.xn.performance.dto.PerformanceStressMachineDto;
import com.xn.performance.service.PerformanceMonitoredMachineService;
import com.xn.performance.service.PerformanceStressMachineService;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/machine")
public class PerformanceMachineController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    @Resource
    PerformanceStressMachineService performanceStressMachineService;
    @Resource
    PerformanceMonitoredMachineService performanceMonitoredMachineService;
    @Resource
    CompanyService companyService;

    @Resource
    DepartmentService departmentService;

    @Resource
    TestSystemService testSystemService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {
        if (path.equals("stress_machine_list")) {
            List<PerformanceStressMachineDto> performanceStressMachineDtoList = null;
            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
            performanceStressMachineDtoList = performanceStressMachineService.list(performanceStressMachineDto);
            model.put("stress_machine_list_all", performanceStressMachineDtoList);
            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            String stressMachineName = request.getParameter("stressMachineName");

            if (isNotEmpty(company) && !company.equals("null")) {
                performanceStressMachineDto.setCompany(company);
            }
            if (isNotEmpty(department) && !department.equals("null")) {
                performanceStressMachineDto.setDepartment(department);
            }
            if (isNotEmpty(psystem) && !psystem.equals("null")) {
                performanceStressMachineDto.setPsystem(psystem);
            }
            if (isNotEmpty(stressMachineName) && !stressMachineName.equals("null")) {
                performanceStressMachineDto.setStressMachineName(stressMachineName);
            }
            performanceStressMachineDtoList = performanceStressMachineService.list(performanceStressMachineDto);
            model.put("stress_machine_list", performanceStressMachineDtoList);

        } else if (path.equals("monitored_machine_list")) {
            List<PerformanceMonitoredMachineDto> performanceMonitoredMachineDtoList = null;
            PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();
            performanceMonitoredMachineDtoList = performanceMonitoredMachineService.list(performanceMonitoredMachineDto);
            model.put("monitored_machine_list_all", performanceMonitoredMachineDtoList);
            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            String stressMachineName = request.getParameter("stressMachineName");

            if (isNotEmpty(company) && !company.equals("null")) {
                performanceMonitoredMachineDto.setCompany(company);
            }
            if (isNotEmpty(department) && !department.equals("null")) {
                performanceMonitoredMachineDto.setDepartment(department);
            }
            if (isNotEmpty(psystem) && !psystem.equals("null")) {
                performanceMonitoredMachineDto.setPsystem(psystem);
            }
            if (isNotEmpty(stressMachineName) && !stressMachineName.equals("null")) {
                performanceMonitoredMachineDto.setMonitoredMachineName(stressMachineName);
            }
            performanceMonitoredMachineDtoList = performanceMonitoredMachineService.list(performanceMonitoredMachineDto);
            model.put("monitored_machine_list", performanceMonitoredMachineDtoList);


        }

        List<CompanyDto> companyDtoList = companyService.list(new CompanyDto());
        List<DepartmentDto> departmentDtoList = departmentService.list(new DepartmentDto());
        List<TestSystemDto> testSystemDtoList = testSystemService.list(new TestSystemDto());
        model.put("companyList", companyDtoList);
        model.put("departmentList", departmentDtoList);
        model.put("psystemList", testSystemDtoList);
        return "/performance/machine/" + path;
    }

    @RequestMapping(value = "/stress_machine_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveStressMachine(PerformanceStressMachineDto performanceStressMachineDto) {
        CommonResult commonResult = new CommonResult();
        try {
            if (!ValidateUtil.validate(performanceStressMachineDto)) {
                logger.warn(String.format("参数有误", performanceStressMachineDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }
            performanceStressMachineService.save(performanceStressMachineDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }

    @RequestMapping(value = "/monitored_machine_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveMonitoredMachine(PerformanceMonitoredMachineDto performanceMonitoredMachineDto) {
        CommonResult commonResult = new CommonResult();
        try {
            if (!ValidateUtil.validate(performanceMonitoredMachineDto)) {
                logger.warn(String.format("参数有误", performanceMonitoredMachineDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }
            performanceMonitoredMachineService.save(performanceMonitoredMachineDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }


    @RequestMapping(value = "/{path}/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteMachine(@PathVariable String path, @RequestParam Integer id) {
        CommonResult commonResult = new CommonResult();
        try {
            if (path.equals("stress_machine_list")) {
                PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
                performanceStressMachineDto.setId(id);
                int n = performanceStressMachineService.delete(performanceStressMachineDto);
                if (n == 0) {
                    commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                    commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                } else {
                    commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
                    commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
                }
            } else if (path.equals("monitored_machine_list")) {
                PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();
                performanceMonitoredMachineDto.setId(id);
                int n = performanceMonitoredMachineService.delete(performanceMonitoredMachineDto);
                if (n == 0) {
                    commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                    commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                } else {
                    commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
                    commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
                }
            }
        } catch (Exception e) {

            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }
    }


}