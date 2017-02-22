package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/2/9.
 */

import com.xn.manage.Enum.PublishEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/performance/script")
public class PerformanceScriptController {

    @RequestMapping(value="/{path}", method = RequestMethod.GET)
    public String getPlanPage(@PathVariable String  path, ModelMap model) {
        List<PublishEnum> publishEnumList=new ArrayList<PublishEnum>();
        for(PublishEnum item:PublishEnum.values()){
            publishEnumList.add(item);
        }
        model.put("publishList",publishEnumList);
        return "/performance/script/" + path;
    }
}

