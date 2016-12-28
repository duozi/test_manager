package com.xn.autotest.bean.resultBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  abstract class Result {
    private static final Logger logger = LoggerFactory.getLogger(Result.class);
    private String name;
    /**
     * 执行时间 ms
     */
    private long duration;
    /**
     * case 运行结果，pass faile error
     */
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
