package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.manage.Enum.CommonResultEnum;
import com.xn.performance.dto.PerformanceStressMachineDto;
import com.xn.performance.service.PerformanceStressMachineService;
import com.xn.performance.util.CommonResult;
import com.xn.performance.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/performance/machine")
public class PerformanceMachineController {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    @Resource
    PerformanceStressMachineService performanceStressMachineService;

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public String common(@PathVariable String path, ModelMap model) {
        if (path.equals("stress_machine_list")) {
            List<PerformanceStressMachineDto> performanceStressMachineDtoList = performanceStressMachineService.list(new PerformanceStressMachineDto());
            model.put("stress_machine_list", performanceStressMachineDtoList);
        } else if (path.equals("monitored_machine_list")) {

        }
        return "/performance/machine/" + path;
    }

    @RequestMapping(value = "/stress_machine_list/save", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveStressMachine(PerformanceStressMachineDto performanceStressMachineDto) {
        CommonResult commonResult = new CommonResult();
        if (!ValidateUtil.validate(performanceStressMachineDto)) {
            logger.warn(String.format("参数有误", performanceStressMachineDto));
            commonResult.setCode(CommonResultEnum.PARAM_ERROR.getReturnCode());
            commonResult.setMessage(CommonResultEnum.PARAM_ERROR.getReturnMsg());
            return commonResult;
        }
        performanceStressMachineService.save(performanceStressMachineDto);
        commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
        commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        return commonResult;
    }

    @RequestMapping(value = "/stress_machine_list/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteStressMachine(@RequestParam Integer id) {
        CommonResult commonResult = new CommonResult();
        PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
        performanceStressMachineDto.setId(id);
        int n = performanceStressMachineService.delete(performanceStressMachineDto);
        if (n == 0) {
            commonResult.setCode(CommonResultEnum.ERROR.getReturnCode());
            commonResult.setMessage(CommonResultEnum.ERROR.getReturnMsg());
        } else {
            commonResult.setCode(CommonResultEnum.SUCCESS.getReturnCode());
            commonResult.setMessage(CommonResultEnum.SUCCESS.getReturnMsg());
        }
        return commonResult;
    }

}

