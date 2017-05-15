package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.performance.api.PerformanceMonitoredMachineService;
import com.xn.performance.api.PerformanceStressMachineService;
import com.xn.performance.dto.PerformanceMonitoredMachineDto;
import com.xn.performance.dto.PerformanceStressMachineDto;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/machine")
public class PerformanceMachineController {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMachineController.class);

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
    Set<String> set = new HashSet<>(); // 非同步，非线程安全的Set
    Set<String> linkedMachine = Collections.synchronizedSet(set); // 返回了一个线程安全的Set

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request,PageInfo pageInfo) {
        pageInfo.setPagination(true);
        pageInfo.setPageSize(15);
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
            PageResult<PerformanceStressMachineDto> stressMachineList=null;
            try {
                stressMachineList = performanceStressMachineService.listByPage(performanceStressMachineDto,pageInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            model.put("stress_machine_list", stressMachineList.getList());
            model.put("page", stressMachineList.getPage());

        } else if (path.equals("monitored_machine_list")) {
            List<PerformanceMonitoredMachineDto> performanceMonitoredMachineDtoList = null;
            PerformanceMonitoredMachineDto performanceMonitoredMachineDto = new PerformanceMonitoredMachineDto();
            performanceMonitoredMachineDtoList = performanceMonitoredMachineService.list(performanceMonitoredMachineDto);
            model.put("monitored_machine_list_all", performanceMonitoredMachineDtoList);
            String company = request.getParameter("company");
            String department = request.getParameter("department");
            String psystem = request.getParameter("psystem");
            String stressMachineName = request.getParameter("monitoredMachineName");

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
            PageResult<PerformanceMonitoredMachineDto> monitoredMachineList=null;
            try {
                monitoredMachineList = performanceMonitoredMachineService.listByPage(performanceMonitoredMachineDto,pageInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            model.put("monitored_machine_list", monitoredMachineList.getList());
            model.put("page", monitoredMachineList.getPage());


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
            String ip=performanceStressMachineDto.getIp();
            String username=performanceStressMachineDto.getUsername();
            String password=performanceStressMachineDto.getPassword();
            String port=performanceStressMachineDto.getPort();
            String key=ip+"_"+username+"_"+password+"_"+port;
            if(linkedMachine.contains(key)){
                performanceStressMachineDto.setStressMachineStatus("未执行");
                performanceStressMachineService.save(performanceStressMachineDto);
            }else{
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());

            }

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
            String ip=performanceMonitoredMachineDto.getIp();
            String username=performanceMonitoredMachineDto.getUsername();
            String password=performanceMonitoredMachineDto.getPassword();
            String port=performanceMonitoredMachineDto.getPort();
            String key=ip+"_"+username+"_"+password+"_"+port;
            if(linkedMachine.contains(key)){

                performanceMonitoredMachineService.save(performanceMonitoredMachineDto);
            }else{
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());

            }

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

    @RequestMapping(value = "/{path}/test", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult testLink(@PathVariable final String path, @RequestParam final String ip, @RequestParam final String username, @RequestParam final String password, @RequestParam final Integer port) {
        final CommonResult commonResult = new CommonResult();
        try {
            if (isNotEmpty(ip) && isNotEmpty(username) && isNotEmpty(password)) {

                if (path.equals("stress_machine_list")) {
                    boolean canLink = performanceStressMachineService.testLink(ip, username, password, port);
                    commonResult.setData(canLink);
                    if(canLink){
                        String key=ip+"_"+username+"_"+password+"_"+port;
                        linkedMachine.add(key);
                    }
                } else if (path.equals("monitored_machine_list")) {
                    boolean canLink = performanceMonitoredMachineService.testLink(ip, username, password, port);
                    commonResult.setData(canLink);
                    if(canLink){
                        String key=ip+"_"+username+"_"+password+"_"+port;
                        linkedMachine.add(key);
                    }
                }

            } else {
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage("有参数为空");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }
    }
}