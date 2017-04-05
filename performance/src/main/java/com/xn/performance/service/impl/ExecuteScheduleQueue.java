package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/16.
 */

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.service.PerformanceResultService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Date;

import static com.xn.performance.service.impl.SpringTask.STRESS_MACHINE_STATUS;
import static com.xn.performance.service.impl.SpringTask.STRESS_MACHINE_WAITING_MAP;


public class ExecuteScheduleQueue extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteScheduleQueue.class);

    @Autowired
    PerformanceResultService performanceResultService;
    @Autowired
    JmeterServiceImpl jmeterService;

    public ExecuteScheduleQueue() {

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //job自动注入
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        //获得传入的参数
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();  // Note the difference from the previous example


        PerformancePlanShowDto performancePlanShowDto = (PerformancePlanShowDto) dataMap.get("performancePlanShowDto");
        logger.info(new Date() +Thread.currentThread().getName()+"=========executeInternal performanceResultDto:"+performancePlanShowDto.toString() );
        Integer stressMachineId = performancePlanShowDto.getStressMachineId();

        Date setStartTime = performancePlanShowDto.getSetStartTime();
        if (setStartTime != null) {
            STRESS_MACHINE_WAITING_MAP.put(stressMachineId, 2);
        }
        Integer nextTask = STRESS_MACHINE_WAITING_MAP.get(stressMachineId);

        while (true) {
            Integer stressMachineStatus = STRESS_MACHINE_STATUS.get(stressMachineId);
            if (stressMachineStatus == null || (stressMachineStatus == 0)) {
                if (nextTask == 2 && setStartTime != null)  {
                    STRESS_MACHINE_STATUS.put(stressMachineId, 1);
                    jmeterService.executeOnce(performancePlanShowDto);

                    break;
                }
            } else {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //给立即执行的任务机会
        STRESS_MACHINE_WAITING_MAP.put(stressMachineId, 1);
        //压力机空闲
        STRESS_MACHINE_STATUS.put(stressMachineId,0);


    }

}
