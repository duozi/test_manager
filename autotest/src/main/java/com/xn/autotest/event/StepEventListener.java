package com.xn.autotest.event;

import com.xn.autotest.command.StepCommand;
import com.xn.autotest.context.Context;
import com.xn.autotest.modle.TestCase;

/**
 * Created by xn056839 on 2016/12/30.
 */
public interface StepEventListener {
    void caseStarted(TestCase testCase, Context context);

    void caseFinished(TestCase testCase, Context context);

    void stepStarted(StepCommand sc);

    void stepFailed(StepCommand sc, Throwable e);

    void stepFinished(StepCommand sc);
}
