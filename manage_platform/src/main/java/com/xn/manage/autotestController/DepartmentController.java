package com.xn.manage.autotestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.common.api.DepartmentService;
import com.xn.common.base.CommonResult;
import com.xn.common.dto.DepartmentDto;
import com.xn.interfacetest.Enum.CommonResultEnum;

@Controller
@RequestMapping("/autotest/manage/department")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/getDepartment")
    @ResponseBody
    public List<DepartmentDto> getDepartment(String id) {
        List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("companyId",id);
        departmentList = departmentService.list(params);
        return departmentList;
    }

    @RequestMapping(value="/saveDepartment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveDepartment(DepartmentDto departmentDto) {
        CommonResult result = new CommonResult();
        try{
            departmentService.save(departmentDto);
        }catch (Exception e){
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message =e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("保存公司异常｛｝",e);
        }
        return  result;
    }

    @RequestMapping(value="/deleteDepartment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteDepartmentBatch(@RequestParam String ids) {
        CommonResult result = new CommonResult();
        List<Long> idList = new ArrayList<Long>();
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            for(String idStr:idArray) {
                if (StringUtils.isNotBlank(idStr)){
                    idList.add(Long.parseLong(idStr));
                }
            }
        }
        try{
            departmentService.deleteBatchByPK(idList);
        }catch (Exception e){
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("删除操作异常｛｝",e);
            e.printStackTrace();
        }
        return  result;
    }

}
