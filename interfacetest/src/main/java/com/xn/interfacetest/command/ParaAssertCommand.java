package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/9/5.
 */

import com.xn.interfacetest.Exception.AssertNotEqualException;
import com.xn.interfacetest.model.AssertKeyValueVo;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParaAssertCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ParaAssertCommand.class);
    private final static String separator = System.getProperty("line.separator", "\r\n");

    private List<AssertKeyValueVo> processedParams;

    private Assert assertItem;

    public ParaAssertCommand(List<AssertKeyValueVo> processedParams) {
        this.processedParams = processedParams;
    }

    public void setAssertItem(Assert assertItem) {
        this.assertItem = assertItem;
    }

    protected void doExecuteParaAssert(Response preResult, List<AssertKeyValueVo> processedParams,Long reportId) throws AssertNotEqualException {
//        try {

            preResult.paraVerify(processedParams, assertItem,reportId);

//        } catch (AssertNotEqualException e) {
//            String message = assertItem.getInterfaceName()+"/"+assertItem.getMethodName()+"/"+assertItem.getCaseName()+"====assert para step invoke has error,expect=" + e + separator + "result=" + preResult;
//            assertItem.setResult("failed");
//            logger.error(message,e);
//            throw e;
//        }
    }
    @Override
    public void execute() throws Exception {

    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws AssertNotEqualException {
        doExecuteParaAssert(assertItem.getResponse(), processedParams,reportId);
    }

}
