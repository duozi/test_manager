package com.xn.autotest.bean.response;/**
 * Created by xn056839 on 2016/12/20.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    Object body;
    Throwable exception;
    public Response() {
    }

    public Response(Object body, Throwable exception) {
        this.exception = exception;
        this.body = body;
    }

}
