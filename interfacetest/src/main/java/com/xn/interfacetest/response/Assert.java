package com.xn.interfacetest.response;
/**
 * Created by xn056839 on 2016/9/7.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Assert {
    private static final Logger logger = LoggerFactory.getLogger(Assert.class);
    private String interfaceName;
    private String methodName;
    private String caseName;
    private String request;
    private Response response;
    private AssertItem diff;
    private String result;

    public Assert(String interfaceName, String methodName, String caseName) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.caseName = caseName;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Throwable getException() {
        return response.exception;
    }

    public void addDiff(AssertItem item) {
        this.diff = item;

    }

    public void addException(Throwable exception) {
        this.response.exception = exception;

    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getCaseName() {
        return caseName;
    }

    public AssertItem getDiff() {
        return diff;
    }
}

