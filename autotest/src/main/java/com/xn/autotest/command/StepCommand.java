package com.xn.autotest.command;/**
 * Created by xn056839 on 2016/12/30.
 */

import com.xn.autotest.bean.response.Response;
import com.xn.autotest.context.Context;
import com.xn.autotest.event.StepNotifier;
import com.xn.autotest.intercept.InterceptorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 抽象的命令执行器,对应case里面的每一个步骤，即每一个步骤就是一个stepCommand
 */
public abstract class StepCommand {
    private static final Logger logger = LoggerFactory.getLogger(StepCommand.class);
    InterceptorFactory interceptor = InterceptorFactory.getInstance();

    StepCommand nextCommand;

    public StepCommand getNextCommand() {
        return nextCommand;
    }

    public void setNextCommand(StepCommand nextCommand) {
        this.nextCommand = nextCommand;
    }

    public boolean hasNextCommand() {
        return null != nextCommand;
    }

    public void execute(Response preResponse, Context context, StepNotifier sNotifier) throws Throwable {
        Response response;
        try {
            interceptor.doBefore(this, preResponse, context);
            sNotifier.fireStepStarted(this);
            response = doExecute(preResponse, context);
            sNotifier.fireStepFinished(this);
        } catch (Throwable e) {
            sNotifier.fireStepFailed(this, e);
            throw e;
        }
        interceptor.doAfter(this, response, context);
        executeNextCommand(response, context, sNotifier);
    }

    public abstract Response doExecute(Response param, Context context) throws Throwable;

    public StepCommand cloneCommand() {
        StepCommand command = doClone();
        if (command != null && hasNextCommand()) {
            command.setNextCommand(getNextCommand().cloneCommand());
        }
        return command;
    }

    protected abstract StepCommand doClone();

    protected void executeNextCommand(Response response, Context context, StepNotifier sNotifier) throws Throwable {
        if (hasNextCommand()) {
            nextCommand.execute(response, context, sNotifier);
        }
    }

    /**
     * 报告输出的具体信息由每个子类自行指定
     * 返回值是Map<String, Object>其中必须包含两个entry：<stepname, 该子类对应的名称>
     * <params, 待输出的参数>,待输出的参数的类型是List<KeyValueStore>,
     * 请参考AssertStepCommand类的toReport()实现
     * 若该子类使用默认的toReport()实现，报告中将不会有该子类的信息
     * 注意CallStepCommand的实现比较特别
     */
    public abstract Map<String, Object> toReport();
}
