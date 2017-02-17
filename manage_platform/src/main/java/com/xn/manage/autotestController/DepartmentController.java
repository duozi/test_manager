package com.xn.manage.autotestController;

import com.xn.company.dto.DepartmentDto;
import com.xn.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/autotest/department")
public class DepartmentController {

    @Autowired
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

}
