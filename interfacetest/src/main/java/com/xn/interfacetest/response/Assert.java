package com.xn.interfacetest.response;
/**
 * Created by xn056839 on 2016/9/7.
 */

import com.xn.interfacetest.dto.TestCaseDto;
import com.xn.interfacetest.dto.TestInterfaceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Assert {
    private static final Logger logger = LoggerFactory.getLogger(Assert.class);
    private TestInterfaceDto interfaceDto;
    private TestCaseDto caseDto;
    private String request;
    private Response response;
    private AssertItem diff;
    private String result;

    public Assert(TestInterfaceDto interfaceDto,TestCaseDto caseDto) {
        this.interfaceDto = interfaceDto;
        this.caseDto = caseDto;
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

    public TestInterfaceDto getInterfaceDto() {
        return interfaceDto;
    }

    public void setInterfaceDto(TestInterfaceDto interfaceDto) {
        this.interfaceDto = interfaceDto;
    }

    public TestCaseDto getCaseDto() {
        return caseDto;
    }

    public void setCaseDto(TestCaseDto caseDto) {
        this.caseDto = caseDto;
    }

    public AssertItem getDiff() {
        return diff;
    }
}

