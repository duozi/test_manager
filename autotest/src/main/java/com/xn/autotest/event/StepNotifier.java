package com.xn.autotest.event;/**
 * Created by xn056839 on 2016/12/30.
 */

import com.xn.autotest.command.StepCommand;
import com.xn.autotest.context.Context;
import com.xn.autotest.modle.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class StepNotifier {
    private static final Logger logger = LoggerFactory.getLogger(StepNotifier.class);

    private Set<StepEventListener> listeners;

    public StepNotifier() {
        this.listeners = new HashSet<StepEventListener>();
    }

    public void addStepEventListener(StepEventListener sListener) {
        this.listeners.add(sListener);
    }

    public void fireCaseStarted(TestCase testCase, Context context) {
        for (StepEventListener listener : listeners) {
            listener.caseStarted(testCase, context);
        }
    }

    public void fireCaseFinished(TestCase testCase, Context context) {
        for (StepEventListener listener : listeners) {
            listener.caseFinished(testCase, context);
        }
    }

    public void fireStepStarted(StepCommand sc) {
        for (StepEventListener listener : this.listeners) {
            listener.stepStarted(sc);
        }
    }

    public void fireStepFailed(StepCommand sc, Throwable e) {
        for (StepEventListener listener : this.listeners) {
            listener.stepFailed(sc, e);
        }
    }

    public void fireStepFinished(StepCommand sc) {
        for (StepEventListener listener : this.listeners) {
            listener.stepFinished(sc);
        }
    }
}
