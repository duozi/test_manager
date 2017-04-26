package com.xn.interfacetest.command;

/**
 * Created by xn056839 on 2016/8/31.
 */
public interface Command {

     void execute() throws Exception;

     void executeWithException() throws Exception;

     void executeWithException(Long reportId) throws Exception;

}
