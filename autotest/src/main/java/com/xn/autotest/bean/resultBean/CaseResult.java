package com.xn.autotest.bean.resultBean;/**
 * Created by xn056839 on 2016/12/20.
 */

import com.xn.autotest.bean.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseResult extends Result {
    private static final Logger logger = LoggerFactory.getLogger(CaseResult.class);
    private String request;
    private Response response;
    private AssertDiff assertDiff;

    /**
     * case在一次执行中运行的次数
     */
    private int excuteTime;

    public CaseResult(String request, Response response, AssertDiff assertDiff, int excuteTime) {
        this.request = request;
        this.response = response;
        this.assertDiff = assertDiff;
        this.excuteTime = excuteTime;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public AssertDiff getAssertDiff() {
        return assertDiff;
    }

    public void setAssertDiff(AssertDiff assertDiff) {
        this.assertDiff = assertDiff;
    }


    public int getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(int excuteTime) {
        this.excuteTime = excuteTime;
    }
}
