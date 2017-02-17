package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.manage.Enum.PerformancePlanStatusEnum;
import com.xn.manage.Enum.PlanStatusEnum;
import com.xn.manage.bean.Company;
import com.xn.manage.bean.Department;
import com.xn.manage.bean.System;
import com.xn.manage.entity.Plan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/performance/plan")
public class PerformancePlanController {

    @RequestMapping(value="/{path}", method = RequestMethod.GET)
    public String getPlanPage(@PathVariable String  path, ModelMap model) {
        List<PerformancePlanStatusEnum> performancePlanStatusEnumList=new ArrayList<PerformancePlanStatusEnum>();
        for(PerformancePlanStatusEnum item:PerformancePlanStatusEnum.values()){
            performancePlanStatusEnumList.add(item);
        }
        model.put("performancePlanStatusEnumList",performancePlanStatusEnumList);

        //公司名称
        List<Company> companyList = new ArrayList<Company>();
        companyList.add(new Company(1,"牛鼎丰"));
        companyList.add(new Company(2,"小牛集团"));
        companyList.add(new Company(3,"小牛在线"));
        companyList.add(new Company(4,"小牛普惠"));
        companyList.add(new Company(5,"新财富"));
        companyList.add(new Company(6,"钱罐子"));

        //系统
        List<System> systemList = new ArrayList<System>();
        systemList.add(new System(1,"风控规则"));
        systemList.add(new System(2,"支付中心"));
        systemList.add(new System(3,"征信公司"));
        systemList.add(new System(4,"商户平台"));

        //部门
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.add(new Department(1,"大数据部"));
        departmentList.add(new Department(2,"平台开发部"));
        departmentList.add(new Department(3,"运维质量部"));
        departmentList.add(new Department(4,"应用开发部"));
        departmentList.add(new Department(5,"质量管控与信息安全部"));

        //计划状态
        List<PlanStatusEnum> planStatusList = new ArrayList<PlanStatusEnum>();
        for(PlanStatusEnum item : PlanStatusEnum.values()){
            planStatusList.add(item);
        }

        //测试计划
        List<Plan> planList = new ArrayList<Plan>();
        planList.add(new Plan(1,"牛贷系统查询联系人测试计划"));


        model.put("planStatusList",planStatusList);
        model.put("planList",planList);
        model.put("systemList", systemList);
        model.put("departmentList", departmentList);
        model.put("companyList", companyList);
        return "/performance/plan/" + path;
    }
}

