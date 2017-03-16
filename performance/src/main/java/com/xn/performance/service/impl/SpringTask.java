package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.xn.performance.dto.*;
import com.xn.performance.service.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.List;
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
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setExecuteStatus("未执行");
        List<PerformancePlanShowDto> list = performanceResultService.getNowTask(performanceResultDto);
        jmeterService.addToNowQueue(list);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void Schedule() {

        logger.info(new Date() + Thread.currentThread().getName() + "============execute schedule task");
        for (Integer stressMachineId : PERFORMANCE_NOW_MAP.keySet()) {
            ConcurrentLinkedQueue queue = PERFORMANCE_NOW_MAP.get(stressMachineId);
            final PerformancePlanShowDto performancePlanShowDto = (PerformancePlanShowDto) queue.peek();
//            获得压力机的状态
            PerformanceStressMachineDto performanceStressMachineDto=new PerformanceStressMachineDto();
            performanceStressMachineDto.setId(stressMachineId);
            performanceStressMachineDto=performanceStressMachineService.get(performanceStressMachineDto);
            String stressMachineStatus=performanceStressMachineDto.getStressMachineStatus();
            //压力机有空并且立即执行的队列第一个任务没有在执行，则执行该任务
            if (stressMachineStatus.equals("未执行")&&performancePlanShowDto.getExecuteStatus().equals("未执行")) {

                //更新队列中的状态
                performancePlanShowDto.setExecuteStatus("执行中");

                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("============" + Thread.currentThread().getName());
                        //更result表新数据库中的内容
                        executeOnce(performancePlanShowDto);
                    }
                });
            }
        }
    }

    //执行一次计划
    public void executeOnce(PerformancePlanShowDto performancePlanShowDto) {
        logger.info("executeOnce============" + Thread.currentThread().getName());
        //如果计划从未执行过，更新计划的状态为执行中，那样就不能修改了
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setId(performancePlanShowDto.getPlanId());
        performancePlanDto = performancePlanService.get(performancePlanDto);
        String planStatus = performancePlanDto.getPlanStatus();
        if (planStatus.equals("未执行")) {
            performancePlanDto.setPlanStatus("执行中");
            performancePlanService.update(performancePlanDto);
        }

        //更新结果的状态为执行中，更新结果的实际执行时间
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setId(performancePlanShowDto.getId());
        performanceResultDto.setExecuteStatus("执行中");
        Date date = new Date();
        performanceResultDto.setActualStartTime(date);
        performanceResultService.update(performanceResultDto);

        //获得脚本
        Integer scriptId = performancePlanShowDto.getScriptId();
        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        performanceScriptDto.setId(scriptId);
        performanceScriptDto = performanceScriptService.get(performanceScriptDto);
        //脚本的地址
        String scriptPath = performanceScriptDto.getPath();
        //获得场景，为了更新脚本
        Integer scenarioId = performancePlanShowDto.getScenarioId();
        PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
        performanceScenarioDto.setId(scenarioId);
        performanceScenarioDto = performanceScenarioService.get(performanceScenarioDto);
        //获得压力机的ip
        Integer stressMachineId = performancePlanShowDto.getStressMachineId();
        PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
        performanceStressMachineDto.setId(stressMachineId);
        performanceStressMachineDto = performanceStressMachineService.get(performanceStressMachineDto);
        //更新压力机的状态为执行中
        performanceStressMachineDto.setStressMachineStatus("执行中");
        performanceStressMachineService.update(performanceStressMachineDto);

        String stressMachineIp = performanceStressMachineDto.getIp();
        //获得结果的id用来标识jemeter脚本
        Integer id = performancePlanShowDto.getId();
        String jmeterScriptPath = generateJmeterScript(scriptPath, performanceScenarioDto, id);

        logger.info(Thread.currentThread().getName() + "==========jmeterScriptPath:" + jmeterScriptPath);
        //使用压力机远程执行
//        jmeterService.execute(stressMachineIp, jmeterScriptPath);
    }

    public String generateJmeterScript(String scriptPath, PerformanceScenarioDto performanceScenarioDto, Integer id) {
        String jmeterScriptPath = "";
        try {

            File script = new File(scriptPath);
            SAXReader saxReader = new SAXReader();

            //读取脚本
            Document document = null;

            try {
                document = saxReader.read(new File(scriptPath));
            } catch (DocumentException e) {
                e.printStackTrace();
            }


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
                Element cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/intProp");
                cycle.setText(cycleString);
            }
            // 输出格式
            OutputFormat outformat = new OutputFormat();
            // 指定XML编码
            outformat.setEncoding("UTF-8");
            outformat.setNewlines(true);
            outformat.setIndent(true);
            outformat.setTrimText(true);

            //上传脚本名称
            String fileName = script.getName();
            //除名称之外的路径
            String path = scriptPath.split(fileName)[0];
            //jmeter脚本的名称
            String jmeterScriptFileName = "jmeter_script_" + id + "_" + fileName;
            jmeterScriptPath = path + jmeterScriptFileName;

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


}
