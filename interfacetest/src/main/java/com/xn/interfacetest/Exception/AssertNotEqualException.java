package com.xn.interfacetest.Exception;/**
 * Created by xn056839 on 2016/9/7.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertNotEqualException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(AssertNotEqualException.class);

    public AssertNotEqualException(String message) {
        super(message);
    }
}
