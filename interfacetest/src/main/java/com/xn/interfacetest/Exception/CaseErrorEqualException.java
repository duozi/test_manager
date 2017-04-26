package com.xn.interfacetest.Exception;/**
 * Created by xn056839 on 2016/9/12.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseErrorEqualException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(CaseErrorEqualException.class);
    public CaseErrorEqualException(String message) {
        super(message);
    }
}
