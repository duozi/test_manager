package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.common.utils.FileUtil;
import com.xn.common.utils.PropertyUtil;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.interfacetest.Enum.CommonResultEnum;
import com.xn.interfacetest.Enum.PublishEnum;
import com.xn.performance.api.PerformanceScriptService;
import com.xn.performance.dto.PerformanceScriptDto;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;


@Controller
@RequestMapping("/performance/script")
public class PerformanceScriptController {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceScriptController.class);
    @Resource
    PerformanceScriptService performanceScriptService;
    @Resource
    CompanyService companyService;
    @Resource
    DepartmentService departmentService;
    @Resource
    TestSystemService testSystemService;



    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model, HttpServletRequest request,PageInfo pageInfo)  {
        StringBuffer pageParams = new StringBuffer(); // 用于页面分页查询的的url参数
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
            pageParams.append("&company=").append(company);
            model.put("company", company);
        }
        if (isNotEmpty(department) && !department.equals("null")) {
            performanceScriptDto.setDepartment(department);
            pageParams.append("&department=").append(department);
            model.put("department", department);
        }
        if (isNotEmpty(psystem) && !psystem.equals("null")) {
            performanceScriptDto.setPsystem(psystem);
            pageParams.append("&psystem=").append(psystem);
            model.put("psystem", psystem);
        }
        if (isNotEmpty(scriptName) && !scriptName.equals("null")) {
            performanceScriptDto.setScriptName(scriptName);
            pageParams.append("&scriptName=").append(scriptName);
            model.put("scriptName", scriptName);
        }
        if (isNotEmpty(scriptStatus) && !scriptStatus.equals("null")) {
            performanceScriptDto.setScriptStatus(scriptStatus);
            pageParams.append("&scriptStatus=").append(scriptStatus);
            model.put("scriptStatus", scriptStatus);
        }
        if (pageInfo.getCurrentPage() < 1) {
            pageInfo.setCurrentPage(1);
        }
        PageResult<PerformanceScriptDto> scriptList=null;
        pageInfo.setPagination(true);
        pageInfo.setPageSize(15);
        pageInfo.setParams(pageParams.toString());

        try {
            scriptList = performanceScriptService.listByPage(performanceScriptDto,pageInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        model.put("script_list", scriptList.getList());
        model.put("page", scriptList.getPage());
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


            performanceScriptDto = performanceScriptService.save(performanceScriptDto);
            Integer id = performanceScriptDto.getId();
            //转存脚本文件
            String tempPath = PropertyUtil.getProperty("upload_path") + "temp" + File.separator + performanceScriptDto.getScriptFileName();

            String savePath = PropertyUtil.getProperty("upload_path") + id + File.separator;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdir();
            }
            File saveFile = new File(savePath + performanceScriptDto.getScriptFileName());
            File tempFile = new File(tempPath);
            Files.move(tempFile.toPath(), saveFile.toPath());

            //转存依赖文件
            String dependenceFileName = performanceScriptDto.getDependenceFileName();
            String[] dependenceNames = dependenceFileName.trim().split(" ");
            for (String fileName : dependenceNames) {
                tempPath = PropertyUtil.getProperty("upload_path") + "temp" + File.separator + fileName;

                savePath = PropertyUtil.getProperty("upload_path") + id + File.separator;

                saveFile = new File(savePath + fileName);
                tempFile = new File(tempPath);
                Files.move(tempFile.toPath(), saveFile.toPath());

            }
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("保存操作异常｛｝", e);
        } finally {
            return commonResult;
        }

    }


    @RequestMapping(value = "/script_list/edit", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult editScript(PerformanceScriptDto performanceScriptDto) {
        CommonResult commonResult = new CommonResult();
        try {
            performanceScriptService.update(performanceScriptDto);
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("编辑操作异常｛｝", e);
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
            logger.error("删除操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }

    @RequestMapping(value = "/script_list/upload_file", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult uploadScript(@RequestParam("uploadScript") MultipartFile[] files, HttpServletRequest request) {
        CommonResult result = new CommonResult();
        result.setMessage("上传成功！");
        String fileName = "";
        String path = "";
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    String name = file.getOriginalFilename();
                    path = PropertyUtil.getProperty("upload_path") + "temp" + File.separator + name;
                    FileUtil.saveFile(file, path);
                    fileName += " " + file.getOriginalFilename();
                }
                result.setData(fileName.trim());
            }
        } catch (Exception e) {
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("上传文件操作异常｛｝", e);
        } finally {
            return result;
        }


    }

    @RequestMapping(value = "/script_list/upload_dependence_file", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult uploadDependenceFile(@RequestParam("dependenceFile") MultipartFile[] files, HttpServletRequest request) {
        CommonResult result = new CommonResult();
        result.setMessage("上传成功！");
        String fileName = "";
        String path = "";
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    String name = file.getOriginalFilename();
                    path = PropertyUtil.getProperty("upload_path") + "temp" + File.separator + name;
                    FileUtil.saveFile(file, path);
                    fileName += " " + file.getOriginalFilename();
                }
                result.setData(fileName.trim());
            }
        } catch (Exception e) {
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("上传文件操作异常｛｝", e);
        } finally {
            return result;
        }


    }


    @RequestMapping(value = "/script_list/show_script", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult showScript(HttpServletRequest request) {

        CommonResult commonResult = new CommonResult();
        try {

            String scriptId = request.getParameter("id");
            String scriptFileName = request.getParameter("script_file_name");
            String path = PropertyUtil.getProperty("upload_path") + scriptId + File.separator + scriptFileName;

            File file = new File(path);
            String content = FileUtil.fileReadeForStr(file);
            commonResult.setData(content);
        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("查看脚本操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }

    @RequestMapping(value = "/script_list/script_detail_save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveScriptDetail(@RequestParam String content, @RequestParam String path) {
        CommonResult commonResult = new CommonResult();
        try {

            FileUtil.fileWrite(path, content);

        } catch (Exception e) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(e.getMessage());
            logger.error("删除操作异常｛｝", e);
        } finally {
            return commonResult;
        }
    }
}

