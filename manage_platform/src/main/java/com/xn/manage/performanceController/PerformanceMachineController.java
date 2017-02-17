package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/performance/machine")
public class PerformanceMachineController {

    @RequestMapping(value="/{path}", method = RequestMethod.GET)
    public String getPlanPage(@PathVariable String  path, ModelMap model) {

        return "/performance/machine/" + path;
    }
}

