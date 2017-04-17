package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/16.
 */

import com.xn.performance.dto.PerformancePlanShowDto;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

/**
 * Quartz调度管理器
 *
 * @author Administrator
 */
public class QuartzManager {
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    @Autowired
    JobDetail jobDetail;


    /**
     * @param sched   调度器
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @Title: QuartzManager.java
     */
    public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, Date time,PerformancePlanShowDto performancePlanShowDto) {
        try {
//            JSONObject jsonObject = JSONObject.fromObject(performancePlanShowDto);
            JobDetail jobDetail = newJob(cls)
                    .withIdentity(jobName, JOB_GROUP_NAME)
//            .usingJobData("param", jsonObject.toString())
                    .build();
            // 触发器
            Map<String, PerformancePlanShowDto> parameters=new HashMap<String, PerformancePlanShowDto>();
            parameters.put("performancePlanShowDto",performancePlanShowDto);

            jobDetail.getJobDataMap().putAll(parameters);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, "test")
                    .startAt(time)
                    .build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * @param sched            调度器
//     * @param jobName          任务名
//     * @param jobGroupName     任务组名
//     * @param triggerName      触发器名
//     * @param triggerGroupName 触发器组名
//     * @param jobClass         任务
//     * @param time             时间设置，参考quartz说明文档
//     * @Description: 添加一个定时任务
//     * @Title: QuartzManager.java
//     */
//    public static void addJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName, @SuppressWarnings("rawtypes") Class jobClass, String time) {
//        try {
//            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
//            // 触发器
//            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
//            trigger.setCronExpression(time);// 触发器时间设定
//            sched.scheduleJob(jobDetail, trigger);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param sched   调度器
//     * @param jobName
//     * @param time
//     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
//     * @Title: QuartzManager.java
//     */
//    @SuppressWarnings("rawtypes")
//    public static void modifyJobTime(Scheduler sched, String jobName, String time) {
//        try {
//            CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
//            if (trigger == null) {
//                return;
//            }
//            String oldTime = trigger.getCronExpression();
//            if (!oldTime.equalsIgnoreCase(time)) {
//                JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);
//                Class objJobClass = jobDetail.getJobClass();
//                removeJob(sched, jobName);
//                addJob(sched, jobName, objJobClass, time);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param sched            调度器 *
//     * @param sched            调度器
//     * @param triggerName
//     * @param triggerGroupName
//     * @param time
//     * @Description: 修改一个任务的触发时间
//     * @Title: QuartzManager.java
//     */
//    public static void modifyJobTime(Scheduler sched, String triggerName, String triggerGroupName, String time) {
//        try {
//            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
//            if (trigger == null) {
//                return;
//            }
//            String oldTime = trigger.getCronExpression();
//            if (!oldTime.equalsIgnoreCase(time)) {
//                CronTrigger ct = (CronTrigger) trigger;
//                // 修改时间
//                ct.setCronExpression(time);
//                // 重启触发器
//                sched.resumeTrigger(triggerName, triggerGroupName);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param sched   调度器
//     * @param jobName
//     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
//     * @Title: QuartzManager.java
//     */
//    public static void removeJob(Scheduler sched, String jobName) {
//        try {
//            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
//            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
//            sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param sched            调度器
//     * @param jobName
//     * @param jobGroupName
//     * @param triggerName
//     * @param triggerGroupName
//     * @Description: 移除一个任务
//     * @Title: QuartzManager.java
//     */
//    public static void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
//        try {
//            sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
//            sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
//            sched.deleteJob(jobName, jobGroupName);// 删除任务
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * @param sched 调度器
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public static void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sched 调度器
     * @Description:关闭所有定时任务
     * @Title: QuartzManager.java
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}