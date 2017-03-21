package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/7.
 */

import com.xn.performance.dto.*;
import com.xn.performance.service.*;
import com.xn.performance.util.jmeter.XNJmeterStartRemot;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.xn.performance.service.impl.SpringTask.PERFORMANCE_NOW_MAP;
import static com.xn.performance.service.impl.SpringTask.PERFORMANCE_SCHEDULE_MAP;


@Service
public class JmeterServiceImpl implements JmeterService {
    private static final Logger logger = LoggerFactory.getLogger(JmeterServiceImpl.class);
    @Autowired
    PerformanceResultService performanceResultService;

    @Autowired
    PerformancePlanService performancePlanService;

    @Autowired
    PerformanceScriptService performanceScriptService;

    @Autowired
    PerformanceScenarioService performanceScenarioService;
    @Autowired
    PerformanceStressMachineService performanceStressMachineService;

    XNJmeterStartRemot xnJmeterStartRemot = new XNJmeterStartRemot();

    public String execute(String stressMachineIp, String jmeterScriptPath, Integer id) {
        String resultPath = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();//获得本机IP
            resultPath = xnJmeterStartRemot.remoteStart(stressMachineIp, ip, jmeterScriptPath, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultPath;
        }
    }


    @Override
    public void executePlan(String executeType, PerformanceResultDto performanceResultDto) {
        logger.info("============start");
        logger.info("============start");
        //立即执行
        if (executeType.equals("now")) {
            PerformancePlanShowDto performancePlanShowDto = performanceResultService.getShow(performanceResultDto);
            if (performancePlanShowDto!=null) {
//            addToNowQueue(performancePlanShowDtoList);
                executeOnce(performancePlanShowDto);
            }
        }
        //定时执行
        else {
            List<PerformancePlanShowDto> performancePlanShowDtoList = performanceResultService.getScheduleTask(performanceResultDto);
            addToScheduleQueue(performancePlanShowDtoList);
        }
    }

    public boolean stopPlan(Integer id, Integer planId) {
        boolean flag = xnJmeterStartRemot.stop(id);

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
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setId(id);
        performanceResultDto.setExecuteStatus("未执行");
        performanceResultService.update(performanceResultDto);
        //更新压力机的状态
        performanceResultDto = performanceResultService.get(performanceResultDto);
        Integer stressMachineId = performanceResultDto.getStressMachineId();
        PerformanceStressMachineDto performanceStressMachineDto = new PerformanceStressMachineDto();
        performanceStressMachineDto.setId(stressMachineId);
        performanceStressMachineDto.setStressMachineStatus("未执行");
        performanceStressMachineService.update(performanceStressMachineDto);
        return flag;
    }

    public void addToNowQueue(List<PerformancePlanShowDto> list) {
        for (PerformancePlanShowDto performancePlanShowDto : list) {
            Integer stressMachineId = performancePlanShowDto.getStressMachineId();
            if (PERFORMANCE_NOW_MAP.containsKey(stressMachineId)) {
                PERFORMANCE_NOW_MAP.get(stressMachineId).add(performancePlanShowDto);
            } else {
                ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
                queue.offer(performancePlanShowDto);
                PERFORMANCE_NOW_MAP.put(stressMachineId, queue);

            }

        }
    }

    public void addToScheduleQueue(List<PerformancePlanShowDto> list) {
        for (PerformancePlanShowDto performancePlanShowDto : list) {
            Integer stressMachineId = performancePlanShowDto.getStressMachineId();
            if (PERFORMANCE_SCHEDULE_MAP.containsKey(stressMachineId)) {
                PERFORMANCE_SCHEDULE_MAP.get(stressMachineId).add(performancePlanShowDto);
            } else {
                ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
                queue.offer(performancePlanShowDto);
                PERFORMANCE_SCHEDULE_MAP.put(stressMachineId, queue);

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
        //更新压力机的状态为执行中
        performanceStressMachineDto.setStressMachineStatus("执行中");
        performanceStressMachineService.update(performanceStressMachineDto);

        String stressMachineIp = performanceStressMachineDto.getIp();
        //获得结果的id用来标识jemeter脚本
        Integer id = performancePlanShowDto.getId();
        String jmeterScriptPath = generateJmeterScript(scriptPath, performanceScenarioDto, id);

        logger.info(Thread.currentThread().getName() + "==========jmeterScriptPath:" + jmeterScriptPath);
        //使用压力机远程执行
        String resultPath = execute(stressMachineIp, jmeterScriptPath, id);

        performanceResultDto.setExecuteStatus("已执行");
        performanceResultDto.setResultPath(resultPath);
        performanceResultService.update(performanceResultDto);


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
                Element cycle = null;
                try {
                    cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/stringProp");
                } catch (Exception e) {
                    cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/intProp");
                }
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
//
//    public static void main(String[] args) {
//        JmeterServiceImpl jmeterService=new JmeterServiceImpl();
//        jmeterService.execute("10.17.2.187","E:\\upload\\jmeter_script_9_线程组.jmx");
//    }

}
