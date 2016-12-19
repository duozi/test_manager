package com.xn.autotest.bean.request;/**
 * Created by xn056839 on 2016/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plan {
    private static final Logger logger = LoggerFactory.getLogger(Plan.class);
    /**
     * 计划的名字
     */
    private String planName;
    /**
     * 计划执行的类型,0表示单接口,1表示串行接口
     */
    private int executeType;
    /**
     * 计划是否发布的类型，0表示未发布，可以修改，1表示已发布不能修改
     */
    private int planType;

    public Plan(String planName, int executeType, int planType) {
        this.planName = planName;
        this.executeType = executeType;
        this.planType = planType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getExecuteType() {
        return executeType;
    }

    public void setExecuteType(int executeType) {
        this.executeType = executeType;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }
}
