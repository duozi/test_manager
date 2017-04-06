package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


@Service
public class SpringTask {
    private static final Logger logger = LoggerFactory.getLogger(SpringTask.class);
    //key 是压力机id value 是结果id的队列
    public static ConcurrentMap<Integer, ConcurrentLinkedQueue> PERFORMANCE_NOW_MAP = new ConcurrentHashMap<>();

    public static ConcurrentMap<Integer, ConcurrentLinkedQueue> PERFORMANCE_SCHEDULE_MAP = new ConcurrentHashMap<>();
    //0代表没有下一个任务，1代表下一个是立即执行的任务，2代表下一个是定时执行的任务
    public static ConcurrentMap<Integer, Integer> STRESS_MACHINE_WAITING_MAP = new ConcurrentHashMap<>();
    //0代表当前空闲，1代表当前在执行任务
    public static ConcurrentMap<Integer, Integer> STRESS_MACHINE_STATUS = new ConcurrentHashMap<>();


    public ExecutorService threadPool = Executors.newFixedThreadPool(5);
    @Resource
    PerformanceResultServiceImpl performanceResultService;

    @Resource
    PerformancePlanServiceImpl performancePlanService;

    @Resource
    JmeterServiceImpl jmeterService;

    @Resource
    PerformanceScriptServiceImpl performanceScriptService;

    @Resource
    PerformanceScenarioServiceImpl performanceScenarioService;

    @Resource
    PerformanceStressMachineServiceImpl performanceStressMachineService;

    //获得要立即执行但是还没有执行的任务
    @PostConstruct
    public void getTask() {
        //获得当前时间五分钟之后的时间，为了防止启动起来之后已经过了定时任务的时间了
        long current = System.currentTimeMillis();
        current += 5 * 60 * 1000;
        Date nowTime = new Date(current);
        logger.info("get time 5 min after now =========" + nowTime);

        List<PerformancePlanShowDto> nowList = performanceResultService.getNowTask(null);
        jmeterService.addToNowQueue(nowList);

        PerformanceResultDto performanceResultDto = new PerformanceResultDto();

        List<PerformancePlanShowDto> scheduleList = performanceResultService.getScheduleTask(null);

        for (PerformancePlanShowDto item : scheduleList) {
            Date setStartTime = item.getSetStartTime();
            String status = item.getExecuteStatus();
            //除去正在执行而中间被打断的任务
            if (status.equals("执行中")) {
                performanceResultDto = new PerformanceResultDto(item.getId());
                performanceResultDto.setExecuteStatus("取消");
                performanceResultService.update(performanceResultDto);
            } else {

                //如果是当前时间已经超过了设置的开始时间，就取消这个任务
                if (nowTime.getTime() > setStartTime.getTime()) {
                    performanceResultDto.setId(item.getId());
                    performanceResultDto.setExecuteStatus("取消");
                    performanceResultService.update(performanceResultDto);
                    logger.info("当前时间大于设置的开始时间，取消执行这个任务，id:" + item.getId() + "  nowTime:" + nowTime.getTime() + "  setStartTime:" + setStartTime.getTime());
                } else {
                    jmeterService.scheduleJob(item);
                }
            }
        }

    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void Schedule() {

        logger.info(new Date() + Thread.currentThread().getName() + "============execute schedule task");
        for (Integer stressMachineId : PERFORMANCE_NOW_MAP.keySet()) {
            ConcurrentLinkedQueue queue = PERFORMANCE_NOW_MAP.get(stressMachineId);
            final Integer resultId = (Integer) queue.peek();
            if (queue.isEmpty()) {
                PERFORMANCE_NOW_MAP.remove(stressMachineId);
            }

            Integer nextTask = STRESS_MACHINE_WAITING_MAP.get(stressMachineId);
            if (resultId != null) {
                if (nextTask == null || nextTask == 1) {
                    Integer stressMachineStatus = STRESS_MACHINE_STATUS.get(stressMachineId);

                    if ((stressMachineStatus == null || stressMachineStatus == 0)) {

                        STRESS_MACHINE_STATUS.put(stressMachineId, 1);
                        //移除当前需要执行的任务
                        queue.poll();

                        threadPool.execute(new Runnable() {
                            @Override
                            public void run() {
                                logger.info(new Date() + Thread.currentThread().getName() + "执行任务============ resultId:" + resultId);
                                PerformanceResultDto performanceResultDto = new PerformanceResultDto(resultId);
                                PerformancePlanShowDto performancePlanShowDto = performanceResultService.getShow(performanceResultDto);
                                jmeterService.executeOnce(performancePlanShowDto);
                            }
                        });

                    }
                }
            }
        }
    }


}
