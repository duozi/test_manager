package com.xn.autotest.bean.resultBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ResultModle {
    private static final Logger logger = LoggerFactory.getLogger(ResultModle.class);
    private Map<String, Result> resultMap;

    public synchronized void addResult(Result result) {
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        resultMap.put(result.getName(), result);
    }

    public Map<String, Result> getResultMap() {
        return resultMap;
    }

    public List<String> getAllName() {
        Map map = getResultMap();
        List<String> name = Lists.newArrayList(map.keySet());
        return name;
    }
}
