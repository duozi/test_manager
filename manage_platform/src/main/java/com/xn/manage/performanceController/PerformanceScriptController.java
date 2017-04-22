package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import static com.xn.manage.utils.StartJMeterAgent_SSH.exec_command;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xn.common.utils.FileUtil;
import com.xn.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xn.common.api.CompanyService;
import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.CompanyDto;
import com.xn.common.dto.DepartmentDto;
import com.xn.interfacetest.api.TestSystemService;
import com.xn.interfacetest.dto.TestSystemDto;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.manage.Enum.PublishEnum;
import com.xn.performance.api.PerformanceScriptService;
import com.xn.performance.dto.PerformanceScriptDto;


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
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

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



            performanceScriptDto=performanceScriptService.save(performanceScriptDto);
            Integer id=performanceScriptDto.getId();
            String tempPath=PropertyUtil.getProperty("upload_path")+"temp"+File.separator;
            String savePath=PropertyUtil.getProperty("upload_path")+id+File.separator;
            final String command="mv "+tempPath+" "+savePath;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("============" + Thread.currentThread().getName());
                    exec_command("10.17.2.137", "root", "xnhack", 65300,command);
                }
            });

            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
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
        String path="";
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    String name=file.getOriginalFilename();
                    path = PropertyUtil.getProperty("upload_path") +"temp"+File.separator+ name;
                    FileUtil.saveFile(request, file,path);
                    fileName+=" "+file.getOriginalFilename();
                }
                result.setData(fileName.trim());
            }
        } catch (Exception e) {
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("上传文件操作异常｛｝", e);
        }finally {
            return  result;
        }


    }

    @RequestMapping(value = "/script_list/upload_dependence_file", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult uploadDependenceFile(@RequestParam("dependenceFile") MultipartFile[] files, HttpServletRequest request) {
        CommonResult result = new CommonResult();
        result.setMessage("上传成功！");
        String fileName = "";
        String path="";
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    String name=file.getOriginalFilename();
                    path = PropertyUtil.getProperty("upload_path") +"temp"+File.separator+ name;
                    FileUtil.saveFile(request, file,path);
                    fileName+=" "+file.getOriginalFilename();
                }
                result.setData(fileName.trim());
            }
        } catch (Exception e) {
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("上传文件操作异常｛｝", e);
        }finally {
            return  result;
        }


    }


    @RequestMapping(value = "/script_list/show_script", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult showScript(HttpServletRequest request) {

        CommonResult commonResult = new CommonResult();
        try {

            String scriptId = request.getParameter("id");
            String scriptFileName = request.getParameter("script_file_name");
            String path= PropertyUtil.getProperty("upload_path") + scriptId + File.separator + scriptFileName;

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

