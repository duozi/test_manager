package com.xn.manage.utils;

import com.xn.common.utils.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 * 用于返回视图的数据模型工具类
 * 
 * @author Ternence
 * @date 2015年1月9日
 */
public class ModelUtils {

    private static final String LIST = "list";
    private static final String PAGE = "page";

    /**
     * 设置数据对象到视图模型
     * 
     * @param pageInfo
     * @param results
     * @date 2015年1月9日
     * @author Ternence
     */
    public static void setResult(ModelMap model, PageInfo pageInfo, Object... results) {
        model.addAttribute(PAGE, pageInfo);
        if (ArrayUtils.isEmpty(results)) {
            return;
        }

        model.addAttribute(LIST, results[0]);
        if (results.length > 1) {
            for (int i = 1; i < results.length; i++) {
                model.addAttribute(LIST + i, results[i]);
            }
        }

    }
}
