package com.xn.interfacetest.command;

import com.xn.interfacetest.response.Response;

/**
 * Created by xn056839 on 2016/10/31.
 */


public interface CaseCommand extends Command {

    Response response = new Response();
    String request = null;
    String casePath = null;

    @Override
    void execute() ;

    Response getResponse();

    String getResult();

    String getRequest();
}
