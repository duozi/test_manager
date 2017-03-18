package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.xn.performance.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;


@Service
public class SpringTask {
    private static final Logger logger = LoggerFactory.getLogger(SpringTask.class);

    public static ConcurrentMap<Integer, ConcurrentLinkedQueue> PERFORMANCE_NOW_MAP = new ConcurrentHashMap<Integer, ConcurrentLinkedQueue>();

    public static ConcurrentMap<Integer, ConcurrentLinkedQueue> PERFORMANCE_SCHEDULE_MAP = new ConcurrentHashMap<Integer, ConcurrentLinkedQueue>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);
    @Autowired
    PerformanceResultService performanceResultService;

    @Autowired
    PerformancePlanService performancePlanService;
    @Autowired
    JmeterService jmeterService;

    @Autowired
    PerformanceScriptService performanceScriptService;

    @Autowired
    PerformanceScenarioService performanceScenarioService;
    @Autowired
    PerformanceStressMachineService performanceStressMachineService;

    //获得要立即执行但是还没有执行的任务
    @PostConstruct
    public void getTask() {
//        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
//        performanceResultDto.setExecuteStatus("未执行");
//
//        List<PerformancePlanShowDto> nowList = performanceResultService.getNowTask(performanceResultDto);
//        jmeterService.addToNowQueue(nowList);
//
//        List<PerformancePlanShowDto> scheduleList = performanceResultService.getScheduleTask(performanceResultDto);
//        jmeterService.addToScheduleQueue(scheduleList);

//        try {
//            SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
//            Scheduler scheduler = gSchedulerFactory.getScheduler();
//
//            String job_name = "动态任务调度";
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
//            String dstr="2017-3-17 18:06:00";
//            Date date=null;
//            try {
//                date=sdf.parse(dstr);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            PerformancePlanShowDto performancePlanShowDto=new PerformancePlanShowDto();
//            performancePlanShowDto.setId(2);
//
//            QuartzManager.addJob(scheduler, job_name, ExecuteScheduleQueue.class, date,performancePlanShowDto);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void Schedule() {

        logger.info(new Date() + Thread.currentThread().getName() + "============execute schedule task");
//        for (Integer stressMachineId : PERFORMANCE_NOW_MAP.keySet()) {
//            ConcurrentLinkedQueue queue = PERFORMANCE_NOW_MAP.get(stressMachineId);
//            final PerformancePlanShowDto performancePlanShowDto = (PerformancePlanShowDto) queue.peek();
////            获得压力机的状态
//            PerformanceStressMachineDto performanceStressMachineDto=new PerformanceStressMachineDto();
//            performanceStressMachineDto.setId(stressMachineId);
//            performanceStressMachineDto=performanceStressMachineService.get(performanceStressMachineDto);
//            String stressMachineStatus=performanceStressMachineDto.getStressMachineStatus();
//            //压力机有空并且立即执行的队列第一个任务没有在执行，则执行该任务
//            if (stressMachineStatus.equals("未执行")&&performancePlanShowDto.getExecuteStatus().equals("未执行")) {
//
//                //更新队列中的状态
//                performancePlanShowDto.setExecuteStatus("执行中");
//
//                threadPool.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        logger.info("============" + Thread.currentThread().getName());
//                        //更result表新数据库中的内容
//                        jmeterService.executeOnce(performancePlanShowDto);
//                    }
//                });
//            }
//        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
        String dstr="2017-3-16 15:00:00";
        Date date=null;
        try {
            date=sdf.parse(dstr);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
