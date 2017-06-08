package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/7.
 */


import com.xn.performance.api.JmeterService;
import com.xn.performance.dto.*;
import com.xn.performance.util.PropertyUtil;
import com.xn.performance.util.jmeter.XNJmeterStartRemot;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static com.xn.performance.service.impl.SpringTask.*;
import static com.xn.performance.util.DateUtil.lastSecond;
import static com.xn.performance.util.PropertyUtil.getProperty;
import static com.xn.performance.util.jmeter.StartJMeterAgent_SSH.upload;
import static org.apache.commons.lang.StringUtils.isNotEmpty;


@Service
public class JmeterServiceImpl implements JmeterService {
    private static final Logger logger = LoggerFactory.getLogger(JmeterServiceImpl.class);
    public static ConcurrentMap<Integer, XNJmeterStartRemot> RUNNING_MAP = new ConcurrentHashMap<Integer, XNJmeterStartRemot>();
    public static ConcurrentMap<Integer, Scheduler> SCHEDULE_JOB_MAP = new ConcurrentHashMap<>();
    public ExecutorService threadPool = Executors.newFixedThreadPool(5);
    @Autowired
    PerformanceResultServiceImpl performanceResultService;

    @Autowired
    PerformancePlanServiceImpl performancePlanService;

    @Autowired
    PerformanceScriptServiceImpl performanceScriptService;

    @Autowired
    PerformanceScenarioServiceImpl performanceScenarioService;
    @Autowired
    PerformanceStressMachineServiceImpl performanceStressMachineService;


    public String execute(String stressMachineIp, String jmeterScriptPath, String scriptDependenceFile, Integer scriptId, Integer id) throws Exception {
        logger.info(Thread.currentThread().getName() + "=============execute " + " id:" + id + " stressMachineIp:" + stressMachineIp + " jmeterScriptPath:" + jmeterScriptPath);
        String resultPath = "";
        try {
            //这个方法只在windows 下有效
            String ip = getProperty("localIp");

            //获得压力机的ip,用户名，密码，端口号
            PerformanceResultDto performanceResultDto = new PerformanceResultDto(id);
            performanceResultDto = performanceResultService.get(performanceResultDto);
            Integer stressMachineId = performanceResultDto.getStressMachineId();
            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto(stressMachineId);
            performanceStressMachineDto = performanceStressMachineService.get(performanceStressMachineDto);
            String host = performanceStressMachineDto.getIp();
            String user = performanceStressMachineDto.getUsername();
            String psw = performanceStressMachineDto.getPassword();
            String port = performanceStressMachineDto.getPort();
            //把脚本依赖的文件上传到压力机的固定路径下
            if (isNotEmpty(scriptDependenceFile)) {
                String dependencePath = PropertyUtil.getProperty("jmeter_dependence_file_path");
                String[] dependenceName = scriptDependenceFile.trim().split(" ");
                for (String name : dependenceName) {
                    String dependenceFilePath = PropertyUtil.getProperty("upload_path") + scriptId + File.separator + name;
                    upload(dependencePath, dependenceFilePath, host, user, psw, Integer.parseInt(port));
                }
            }

            XNJmeterStartRemot xnJmeterStartRemot = new XNJmeterStartRemot(id, host, user, psw, port);
            RUNNING_MAP.put(id, xnJmeterStartRemot);

            resultPath = xnJmeterStartRemot.rstart_csv(stressMachineIp, ip, jmeterScriptPath);


            return resultPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }
    }


    @Override
    public void executePlan(String executeType, PerformanceResultDto performanceResultDto) {
        logger.info(Thread.currentThread().getName() + "============executePlan executeType:" + executeType + " performanceResultDto:" + performanceResultDto.toString());
        PerformancePlanShowDto performancePlanShowDto = performanceResultService.getShow(performanceResultDto);

        if (performancePlanShowDto != null) {
            //立即执行
            if (executeType.equals("now")) {
                addToNowQueue(performancePlanShowDto);
//                executeOnce(performancePlanShowDto);

                //压力机空闲的时候，取消等待一分钟
                Integer stressMachineId = performancePlanShowDto.getStressMachineId();
                Integer resultId = performancePlanShowDto.getId();
                doJobAsStressMachineId(stressMachineId, resultId);
            }
            //定时执行
            else {

                scheduleJob(performancePlanShowDto);
            }
        }

    }

    public void scheduleJob(PerformancePlanShowDto performancePlanShowDto) {
        logger.info(new Date() + Thread.currentThread().getName() + " ===========scheduleJob performancePlanShowDto:" + performancePlanShowDto.toString());

        try {
            SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = gSchedulerFactory.getScheduler();

            Integer id = performancePlanShowDto.getId();
            String job_name = "定时任务_" + id;

            Date date = performancePlanShowDto.getSetStartTime();

            QuartzManager.addJob(scheduler, job_name, ExecuteScheduleQueue.class, date, performancePlanShowDto);
            SCHEDULE_JOB_MAP.put(id, scheduler);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public void stopPlan(Integer id, Integer planId, String executeStatus) {
        if (executeStatus.equals("执行中")) {
            XNJmeterStartRemot xnJmeterStartRemot = RUNNING_MAP.get(id);
            xnJmeterStartRemot.stop();
            RUNNING_MAP.remove(id);
            PerformancePlanDto performancePlanDto = new PerformancePlanDto();
            performancePlanDto.setId(planId);
            performancePlanDto = performancePlanService.get(performancePlanDto);
            String planStatus = performancePlanDto.getPlanStatus();
            //更新计划的状态，如果这是第一次执行，那还是退回到未执行的状态，计划还可以修改，如果是已经执行过，就不修改计划的状态，还是已执行，不能修改计划
            if (planStatus.equals("执行中")) {
                performancePlanDto.setPlanStatus("未执行");
                performancePlanService.update(performancePlanDto);
            }
            //更新单次执行状态
            PerformanceResultDto performanceResultDto = new PerformanceResultDto(id);
            performanceResultDto.setExecuteStatus("中断");
            performanceResultService.update(performanceResultDto);
            //更新压力机的状态
            performanceResultDto = performanceResultService.get(performanceResultDto);
            Integer stressMachineId = performanceResultDto.getStressMachineId();
            Date actualStartTime = performanceResultDto.getActualStartTime();
            PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
            performanceStressMachineDto.setId(stressMachineId);
            performanceStressMachineDto.setStressMachineStatus("未执行");
            performanceStressMachineService.update(performanceStressMachineDto);


            Date actualEndTime = new Date();
            performanceResultDto.setActualEndTime(actualEndTime);
            String resultPath = getProperty("reports") + id;
            performanceResultDto.setResultPath(resultPath);
            performanceResultService.update(performanceResultDto);
//            Integer executeTime = lastSecond(actualStartTime, actualEndTime);
//            //时序数据保存到本地
//            influxdb_to_sqlite3("telegraf", executeTime, id);
//            influxdb_to_sqlite3("jmeter", executeTime, id);

            //更新压力机map
            STRESS_MACHINE_STATUS.put(stressMachineId, 0);
        } else if (executeStatus.equals("等待中")) {
            PerformanceResultDto performanceResultDto = new PerformanceResultDto(id);
            performanceResultDto = performanceResultService.get(performanceResultDto);
            Date setStartTime = performanceResultDto.getSetStartTime();
            //定时任务
            if (setStartTime != null) {

                Scheduler scheduler = SCHEDULE_JOB_MAP.get(id);
                try {
                    scheduler.shutdown();
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
            //立即执行的任务
            else {
                Integer stressMachineId = performanceResultDto.getStressMachineId();
                ConcurrentLinkedQueue list = PERFORMANCE_NOW_MAP.get(stressMachineId);
                list.remove(id);

            }
            performanceResultDto.setExecuteStatus("取消");
            performanceResultService.update(performanceResultDto);
        }
    }

    public void addToNowQueue(List<PerformancePlanShowDto> list) {
        for (PerformancePlanShowDto performancePlanShowDto : list) {
            String status = performancePlanShowDto.getExecuteStatus();
            //除去正在执行而中间被打断的任务
            if (status.equals("执行中")) {
                PerformanceResultDto performanceResultDto = new PerformanceResultDto(performancePlanShowDto.getId());
                performanceResultDto.setExecuteStatus("取消");
                performanceResultService.update(performanceResultDto);
            } else {
                addToNowQueue(performancePlanShowDto);
            }
        }
    }

    public void addToNowQueue(PerformancePlanShowDto performancePlanShowDto) {
        Integer stressMachineId = performancePlanShowDto.getStressMachineId();
        if (PERFORMANCE_NOW_MAP.containsKey(stressMachineId)) {
            PERFORMANCE_NOW_MAP.get(stressMachineId).add(performancePlanShowDto.getId());
        } else {
            ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
            queue.offer(performancePlanShowDto.getId());
            PERFORMANCE_NOW_MAP.put(stressMachineId, queue);

        }
    }

    //废弃了
    public void addToScheduleQueue(List<PerformancePlanShowDto> list) {
        for (PerformancePlanShowDto performancePlanShowDto : list) {
            String status = performancePlanShowDto.getExecuteStatus();
            //除去正在执行而中间被打断的任务
            if (status.equals("执行中")) {
                PerformanceResultDto performanceResultDto = new PerformanceResultDto(performancePlanShowDto.getId());
                performanceResultDto.setExecuteStatus("取消");
                performanceResultService.update(performanceResultDto);
            } else {
                addToScheduleQueue(performancePlanShowDto);
            }
        }
    }

    //废弃了
    public void addToScheduleQueue(PerformancePlanShowDto performancePlanShowDto) {
        Integer stressMachineId = performancePlanShowDto.getStressMachineId();
        if (PERFORMANCE_SCHEDULE_MAP.containsKey(stressMachineId)) {
            PERFORMANCE_SCHEDULE_MAP.get(stressMachineId).add(performancePlanShowDto.getId());
        } else {
            ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
            queue.offer(performancePlanShowDto.getId());
            PERFORMANCE_SCHEDULE_MAP.put(stressMachineId, queue);

        }
    }


    //执行一次计划
    public void executeOnce(PerformancePlanShowDto performancePlanShowDto) {
        logger.info(Thread.currentThread().getName() + "============executeOnce" + performancePlanShowDto.toString());


        //如果计划从未执行过，更新计划的状态为执行中，那样就不能修改了
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setId(performancePlanShowDto.getPlanId());
        performancePlanDto = performancePlanService.get(performancePlanDto);
        String planStatus = performancePlanDto.getPlanStatus();
        if (planStatus.equals("未执行")) {
            performancePlanDto.setPlanStatus("执行中");
            performancePlanService.update(performancePlanDto);
        }
        Integer id = performancePlanShowDto.getId();
        PerformanceResultDto performanceResultDto = new PerformanceResultDto(id);

        //更新结果的状态为执行中，更新结果的实际执行时间
        performanceResultDto.setExecuteStatus("执行中");
        Date actualStartTime = new Date();
        performanceResultDto.setActualStartTime(actualStartTime);
        performanceResultService.update(performanceResultDto);

        //获得脚本
        Integer scriptId = performancePlanShowDto.getScriptId();
        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        performanceScriptDto.setId(scriptId);
        performanceScriptDto = performanceScriptService.get(performanceScriptDto);
        //脚本的地址
        String scriptName = performanceScriptDto.getScriptFileName();
        String scriptPath = PropertyUtil.getProperty("upload_path") + scriptId + File.separator + scriptName;
        //脚本依赖的文件
        String dependenceFile = performanceScriptDto.getDependenceFileName();

        //更新脚本状态
        String scriptStatus = performanceScriptDto.getScriptStatus();
        if (scriptStatus.equals("未发布")) {
            performanceScriptDto.setScriptStatus("已发布");
            performanceScriptService.update(performanceScriptDto);
        }


        //更新压力机的状态为执行中
        Integer stressMachineId = performancePlanShowDto.getStressMachineId();
        PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
        performanceStressMachineDto.setId(stressMachineId);
        performanceStressMachineDto.setStressMachineStatus("执行中");
        performanceStressMachineService.update(performanceStressMachineDto);
        //获得压力机的ip
        performanceStressMachineDto = performanceStressMachineService.get(performanceStressMachineDto);
        String stressMachineIp = performanceStressMachineDto.getIp();
        //获得结果的id用来标识jemeter脚本

        //获得场景，为了更新状态
        Integer scenarioId = performancePlanShowDto.getScenarioId();
        PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
        performanceScenarioDto.setId(scenarioId);
        if (scenarioId != 0) {


            performanceScenarioDto = performanceScenarioService.get(performanceScenarioDto);
            String scenarioStatus = performanceScenarioDto.getScenarioStatus();
            if (scenarioStatus.equals("未发布")) {
                performanceScenarioDto.setScenarioStatus("已发布");
                performanceScenarioService.update(performanceScenarioDto);
            }
        }
        String jmeterScriptPath = generateJmeterScript(scriptPath, performanceScenarioDto, id);

        logger.info(Thread.currentThread().getName() + "==========jmeterScriptPath:" + jmeterScriptPath);
        logger.info("stressMachineIp=========" + stressMachineIp);
        //使用压力机远程执行

        String resultPath = null;
        try {
            resultPath = execute(stressMachineIp, jmeterScriptPath, dependenceFile, scriptId, id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(new Date() + Thread.currentThread().getName() + "结束了===========" + id);

            RUNNING_MAP.remove(id);

            //更新执行失败的结果
            //更新结果
            performanceResultDto.setExecuteStatus("执行失败");
            performanceResultService.update(performanceResultDto);
            //更新压力机
            performanceStressMachineDto.setStressMachineStatus("未执行");
            performanceStressMachineService.update(performanceStressMachineDto);
            //更新计划，如果是第一次执行的计划，但是失败了，就还原为未执行，这样还可以修改
            PerformancePlanDto performancePlanDto2 = new PerformancePlanDto();
            performancePlanDto2.setId(performancePlanShowDto.getPlanId());
            performancePlanDto2 = performancePlanService.get(performancePlanDto);
            planStatus = performancePlanDto2.getPlanStatus();
            if (planStatus.equals("执行中")) {
                performancePlanDto2.setPlanStatus("未执行");
                performancePlanService.update(performancePlanDto2);
            }
            //更新压力机map
            STRESS_MACHINE_STATUS.put(stressMachineId, 0);

            return;

        }


        logger.info(new Date() + Thread.currentThread().getName() + "结束了===========" + id);

        XNJmeterStartRemot xnJmeterStartRemot = RUNNING_MAP.get(id);
        boolean interrupt = xnJmeterStartRemot.isInterrupt();
        RUNNING_MAP.remove(id);

        //不是被强制停止而结束运行的
        if (!interrupt) {
            //更新结果
            Date actualEndTime = new Date();
            performanceResultDto.setActualEndTime(actualEndTime);
            Integer executeTime = lastSecond(actualStartTime, actualEndTime);
            //时序数据保存到本地
//            influxdb_to_sqlite3("telegraf", executeTime, id);
//            influxdb_to_sqlite3("jmeter", executeTime, id);
            performanceResultDto.setExecuteTime(executeTime);

            performanceResultDto.setExecuteStatus("已执行");
            performanceResultDto.setResultPath(resultPath);
            performanceResultService.update(performanceResultDto);
            //更新压力机
            performanceStressMachineDto.setStressMachineStatus("未执行");
            performanceStressMachineService.update(performanceStressMachineDto);
            //更新计划
            performancePlanDto.setPlanStatus("已执行");
            performancePlanService.update(performancePlanDto);
            //更新压力机map
            STRESS_MACHINE_STATUS.put(stressMachineId, 0);
        }


    }


    public void doJob() {
        logger.info(new Date() + Thread.currentThread().getName() + "============execute schedule task");
        for (Integer stressMachineId : PERFORMANCE_NOW_MAP.keySet()) {
            doJobAsStressMachineId(stressMachineId,null);
        }
    }


    public void doJobAsStressMachineId(Integer stressMachineId, Integer id) {
        ConcurrentLinkedQueue queue = PERFORMANCE_NOW_MAP.get(stressMachineId);
        final Integer resultId = (Integer) queue.peek();
        if (queue.isEmpty()) {
            PERFORMANCE_NOW_MAP.remove(stressMachineId);
        }
        //id==null 为了给所有的立即执行的任务使用， id != null && id == resultId给节省一分钟使用，就是对特定的压力机而言，下次要执行的任务正好就是当前的任务
        if (id == null || (id != null && id == resultId)) {
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
                                executeOnce(performancePlanShowDto);
                            }
                        });

                    }
                }
            }
        }


    }

    public String generateJmeterScript(String scriptPath, PerformanceScenarioDto performanceScenarioDto, Integer id) {

        logger.info(Thread.currentThread().getName() + "=============generateJmeterScript " + " id:" + id + " scriptPath:" + scriptPath + " performanceScenarioDto:" + performanceScenarioDto.toString());
        String jmeterScriptPath = "";
        File script = new File(scriptPath);
        //上传脚本名称
        String fileName = script.getName();
        //除名称之外的路径
        String path = scriptPath.split(fileName)[0];
        //jmeter脚本的名称
        String jmeterScriptFileName = "jmeter_script_" + id + "_" + fileName;
        jmeterScriptPath = path + jmeterScriptFileName;
        Integer scenarioId = performanceScenarioDto.getId();

        try {

            SAXReader saxReader = new SAXReader();

            //读取脚本
            org.dom4j.Document document = null;

            try {
                document = saxReader.read(new File(scriptPath));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            //不修该场景
            if (scenarioId != 0) {

                Element scenario = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup");
                // 获取所有子元素
                List<Element> list = scenario.elements();
                for (Element element : list) {
                    String name = element.attribute("name").getValue();
                    //线程数
                    if (name.equals("ThreadGroup.num_threads")) {
                        element.setText(String.valueOf(performanceScenarioDto.getConcurrency()));
                    }
                    //所有线程在多少时间内启动完毕
                    else if (name.equals("ThreadGroup.ramp_time")) {

                        element.setText(String.valueOf(performanceScenarioDto.getStartup()));
                    }

                    //是否调用执行器
                    else if (name.equals("ThreadGroup.scheduler")) {
                        String scheduler = String.valueOf(performanceScenarioDto.getScheduler());
                        element.setText(scheduler);
                    }

                    //开始时间
                    else if (name.equals("ThreadGroup.start_time")) {
                        Date startTime = performanceScenarioDto.getSetStartTime();
                        if (startTime != null) {
                            String setStartTime = String.valueOf(startTime.getTime());
                            element.setText(setStartTime);
                        }

                    }
                    //结束时间
                    else if (name.equals("ThreadGroup.end_time")) {
                        Date endTime = performanceScenarioDto.getSetEndTime();
                        if (endTime != null) {
                            String setEndTime = String.valueOf(endTime.getTime());
                            element.setText(setEndTime);
                        }

                    }
                    //持续时间
                    else if (name.equals("ThreadGroup.duration")) {
                        String executeTime = String.valueOf(performanceScenarioDto.getExecuteTime());
                        if (!executeTime.equals("null")) {
                            element.setText(executeTime);
                        }

                    }
                    //延迟时间
                    else if (name.equals("ThreadGroup.delay")) {
                        String delayTime = String.valueOf(performanceScenarioDto.getDelayTime());
                        if (!delayTime.equals("null")) {
                            element.setText(delayTime);
                        }

                    }


                }

                //循环数
                String cycleString = String.valueOf(performanceScenarioDto.getCycle());
                if (!cycleString.equals("null")) {
                    Element cycle = null;
                    try {
                        cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/stringProp");
                    } catch (Exception e) {
                        cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/intProp");
                    }
                    cycle.setText(cycleString);
                }
            }
            // 输出格式
            OutputFormat outformat = new OutputFormat();
            // 指定XML编码
            outformat.setEncoding("UTF-8");

            outformat.setNewLineAfterDeclaration(false);
            outformat.setNewlines(false);

            outformat.setIndent(true);
            //不替换文件中的空行和空格
            outformat.setTrimText(false);

            OutputStream out = new FileOutputStream(jmeterScriptPath);
            XMLWriter xmlwriter = new XMLWriter(out, outformat);
            xmlwriter.write(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jmeterScriptPath;
        }
    }

    public static void main(String[] args) {
        JmeterServiceImpl jmeterService = new JmeterServiceImpl();
        PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
        performanceScenarioDto.setCycle(1);
        performanceScenarioDto.setScheduler("false");
        performanceScenarioDto.setStartup(5);
        performanceScenarioDto.setConcurrency(5);
        jmeterService.generateJmeterScript("D:\\origin.jmx", performanceScenarioDto, 5);
    }

}
