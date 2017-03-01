package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.dto.DepartmentDto;
import com.xn.common.company.service.CompanyService;
import com.xn.common.company.service.DepartmentService;
import com.xn.common.utils.FileUtil;
import com.xn.common.utils.PropertyUtil;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.service.TestSystemService;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.PublishEnum;
import com.xn.performance.dto.PerformanceScriptDto;
import com.xn.performance.service.PerformanceScriptService;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/performance/script")
public class PerformanceScriptController {

    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
    @Resource
    PerformanceScriptService performanceScriptService;
    @Resource
    CompanyService companyService;

    @Resource
    DepartmentService departmentService;

    @Resource
    TestSystemService testSystemService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request) {
        List<PerformanceScriptDto> performanceScriptDtoList = null;
        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
        model.put("script_list_all", performanceScriptDtoList);
        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String psystem = request.getParameter("psystem");
        String scriptName = request.getParameter("scriptName");
        String scriptStatus = request.getParameter("scriptStatus");

        if (isNotEmpty(company) && !company.equals("null")) {
            performanceScriptDto.setCompany(company);
        }
        if (isNotEmpty(department) && !department.equals("null")) {
            performanceScriptDto.setDepartment(department);
        }
        if (isNotEmpty(psystem) && !psystem.equals("null")) {
            performanceScriptDto.setPsystem(psystem);
        }
        if (isNotEmpty(scriptName) && !scriptName.equals("null")) {
            performanceScriptDto.setScriptName(scriptName);
        }
        if (isNotEmpty(scriptStatus) && !scriptStatus.equals("null")) {
            performanceScriptDto.setScriptStatus(scriptStatus);
        }
        performanceScriptDtoList = performanceScriptService.list(performanceScriptDto);
        model.put("script_list", performanceScriptDtoList);
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

        return "/performance/script/" + path;
    }

    @RequestMapping(value = "/script_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveScript(PerformanceScriptDto performanceScriptDto) {
        CommonResult commonResult = new CommonResult();
        try {
            performanceScriptDto.setScriptStatus(PublishEnum.UNPUBLISHED.getName());
            String fileName = "e:\\\\upload\\\\" + performanceScriptDto.getPath();
            performanceScriptDto.setPath(fileName);

            if (!ValidateUtil.validate(performanceScriptDto)) {
                logger.warn(String.format("参数有误", performanceScriptDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

            performanceScriptService.save(performanceScriptDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }


    @RequestMapping(value = "/script_list/edit", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult editScript(PerformanceScriptDto performanceScriptDto) {
        CommonResult commonResult = new CommonResult();
        try {
            if (!ValidateUtil.validate(performanceScriptDto)) {
                logger.warn(String.format("参数有误", performanceScriptDto));
                commonResult.setCode(CommonResultEnum.FAILED.getReturnCode());
                commonResult.setMessage(CommonResultEnum.FAILED.getReturnMsg());
                return commonResult;
            }

            performanceScriptService.update(performanceScriptDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }

    }

    @RequestMapping(value = "/script_list/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteScript(@RequestParam Integer id) {
        CommonResult commonResult = new CommonResult();
        try {

            PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
            performanceScriptDto.setId(id);
            int n = performanceScriptService.delete(performanceScriptDto);
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

    @RequestMapping(value = "/script_list/upload_script", method = RequestMethod.POST)
    @ResponseBody
    public String uploadScript(@RequestParam("uploadScript") MultipartFile[] files, HttpServletRequest request) {
        String path = "";
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                // 保存文件
                FileUtil.saveFile(request, file);
                path = PropertyUtil.getProperty("upload_path") + file.getOriginalFilename();
            }
        }
        // 重定向
        return path;
    }


    @RequestMapping(value = "/script_list/show_script", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult showScript( HttpServletRequest request) {
        CommonResult commonResult = new CommonResult();
        try {
            String path = request.getParameter("path");
            File file = new File(path);
            String content = FileUtil.fileReadeForStr(file);
            commonResult.setData(content);
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }
    }

    @RequestMapping(value = "/script_list/script_detail_save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveScriptDetail(@RequestParam String content,@RequestParam String path) {
        CommonResult commonResult = new CommonResult();
        try {

            FileUtil.fileWrite(path, content);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
        } finally {
            return commonResult;
        }
    }
}

