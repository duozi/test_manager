package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/16.
 */

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;
import com.xn.performance.service.PerformanceResultService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Date;

@Component
public class ExecuteScheduleQueue extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteScheduleQueue.class);
    @Autowired
    PerformanceResultService performanceResultService;

    public ExecuteScheduleQueue() {

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //job自动注入
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        logger.info("***********");


        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();  // Note the difference from the previous example

        //执行完更新结果表
        PerformancePlanShowDto performancePlanShowDto = (PerformancePlanShowDto) dataMap.get("performancePlanShowDto");
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setActualEndTime(new Date());


        performanceResultDto.setActualStartTime(jobExecutionContext.getFireTime());
        performanceResultDto.setExecuteTime((int) (jobExecutionContext.getJobRunTime() / 1000));
        performanceResultDto.setExecuteStatus("已执行");
        performanceResultDto.setId(2);
        performanceResultService.update(performanceResultDto);


    }

//    private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";
//
//    private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
//        ApplicationContext appCtx = null;
//        appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
//        if (appCtx == null) {
//            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
//        }
//        return appCtx;
//    }


}
