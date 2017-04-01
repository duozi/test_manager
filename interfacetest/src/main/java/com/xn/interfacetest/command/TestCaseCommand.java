package com.xn.interfacetest.command;/**
 * Created by xn056839 on 2016/9/5.
 */

import com.xn.interfacetest.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestCaseCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(TestCaseCommand.class);

    private CaseCommand caseCommand;
    private AssertCommand assertCommand;
    private List<Command> beforeCommand;
    private List<Command> afterCommand;


    public void setCaseCommand(CaseCommand CaseCommand) {
        this.caseCommand = CaseCommand;
    }


    public void setAssertCommand(AssertCommand assertCommand) {
        this.assertCommand = assertCommand;
    }

    public void setBeforeCommand(List<Command> beforeCommand) {
        this.beforeCommand = beforeCommand;
    }

    public void setAfterCommand(List<Command> afterCommand) {
        this.afterCommand = afterCommand;
    }


    @Override
    public void execute(Long caseId,Long interfaceId,Long planId,Long reportId)  {
        if (caseCommand != null) {
            if (beforeCommand != null) {

                for (Command command : beforeCommand) {
                    command.execute();
                }
            }

            caseCommand.execute( caseId, interfaceId, planId, reportId);
            //响应内容
            Response response = caseCommand.getResponse();
            //请求参数
            String request = caseCommand.getRequest();
            //请求结果---失败还是异常
            String result = caseCommand.getResult();
            assertCommand.setAssertItem(request, response, result);
            assertCommand.execute();
            if (afterCommand != null) {
                for (Command command : afterCommand) {
                    command.execute();
                }
            }
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }
}
