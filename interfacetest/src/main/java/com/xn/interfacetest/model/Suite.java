package com.xn.interfacetest.model;
/**
 * Created by xn056839 on 2016/9/2.
 */

import com.xn.interfacetest.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Suite {
    private static final Logger logger = LoggerFactory.getLogger(Suite.class);
    public static ExecutorService exe = Executors.newFixedThreadPool(80);
    List<Command> beforeClass;
    List<Command> afterClass;
    List<Command> testCase;


    public Suite(List<Command> beforeClass, List<Command> afterClass, List<Command> testCase) {
        this.beforeClass = beforeClass;

        this.afterClass = afterClass;


        this.testCase = testCase;
    }

    public Suite() {
    }

    public static Logger getLogger() {
        return logger;
    }

    public List<Command> getBeforeClass() {
        return beforeClass;
    }

    public void setBeforeClass(List<Command> beforeClass) {
        this.beforeClass = beforeClass;
    }

    public List<Command> getAfterClass() {
        return afterClass;
    }

    public void setAfterClass(List<Command> afterClass) {
        this.afterClass = afterClass;
    }

    public List<Command> getTestCase() {
        return testCase;
    }

    public void setTestCase(List<Command> testCase) {
        this.testCase = testCase;
    }

    public void execute() throws Exception{
        if (beforeClass != null) {

            for(Command command:beforeClass){
                command.execute();
            }
            if (testCase != null) {
                for (int i = 0; i < testCase.size(); i++) {
                    final int finalI = i;
                    exe.execute(new Runnable() {
                                    @Override
                                    public void run() {
//                                        addSize();
                                        try {
                                            testCase.get(finalI).execute();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                    );

                }
                try {
//                    System.out.println("sleep------------");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
//                    System.out.println("inter----------");
                }

                exe.shutdown();
                while (true) {
//                    if(size==ReportResult.getReportResult().getTotal()){
//
//                    }

                    if (exe.isTerminated()) {
//                        System.out.println("-----------");
                        if (afterClass != null) {

                            for(Command command:afterClass){
                                command.execute();
                            }
                        }
//                        ReportResult.getReportResult().setStopTime(new Date());
                        break;
                    }

                }

            }

        }
    }
}